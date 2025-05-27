package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.personaDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.DAO.ReporteDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.ValidadorFechas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistroServlet extends HttpServlet {

    private personaDAO PersonaDAO;
    private registroDAO RegistroDAO;

    @Override
    public void init() throws ServletException{
        PersonaDAO = new personaDAO();
        RegistroDAO = new registroDAO();

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrarPersona(request, response);

        }else {
            response.sendRedirect("JSP/error.jsp");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("listarRegistros".equals(accion)) {
            listarRegistros(request, response);
        } else {
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 1. Validar sesión de usuario
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("JSP/login.jsp");
            return;
        }
        // 2. Obtener parámetros de la solicitud
        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");
        String tipoRegistroPer = request.getParameter("tipoRegistro");
        String fechaPer = request.getParameter("fechaPersona");

        try {
            ValidadorFechas.validarFechaNoFutura(fechaPer);
        } catch (IllegalArgumentException e) {
            enviarError(request, response, e.getMessage());
            return;
        }
        String horaPer = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        // 3. Validaciones iniciales
        boolean rutValido = PersonaDAO.buscarRut(rutPer);
        boolean nombreVerificado = PersonaDAO.buscarNombre(nombrePer, rutPer);

        // 4. Lógica principal
        if (rutValido) {
            procesarRegistroExistente(request, response, usuario, rutPer, nombrePer, tipoRegistroPer,
                    fechaPer, horaPer, nombreVerificado);
        } else {
            procesarNuevoRegistro(request, response, usuario, rutPer, nombrePer, tipoRegistroPer, fechaPer, horaPer);
        }
    }

    private void procesarRegistroExistente(HttpServletRequest request, HttpServletResponse response,
                                           Usuario usuario, String rutPer, String nombrePer,
                                           String tipoRegistroPer, String fechaPer, String horaPer,
                                           boolean nombreVerificado) throws IOException, ServletException {
        if (!nombreVerificado) {
            enviarError(request, response, "El nombre ingresado no coincide con el rut del Sistema");
            return;
        }

        // Validar alternancia de registros
        String ultimoTipo = RegistroDAO.obtenerUltimoTipoRegistroGeneral(rutPer);

        if (ultimoTipo == null && !tipoRegistroPer.equals("INGRESO")) {
            enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }

        if (ultimoTipo != null && ultimoTipo.equals(tipoRegistroPer)) {
            enviarError(request, response,
                    "No puedes registrar dos " + tipoRegistroPer + " consecutivos. Debes alternar entre INGRESO y SALIDA.");
            return;
        }

        // Registrar el movimiento
        Registro registro = crearRegistro(rutPer, usuario.getIdUsuario(), fechaPer, tipoRegistroPer, horaPer);
        RegistroDAO.registrar(registro);
        response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }

    private void procesarNuevoRegistro(HttpServletRequest request, HttpServletResponse response,
                                       Usuario usuario, String rutPer, String nombrePer,
                                       String tipoRegistroPer, String fechaPer, String horaPer)
            throws IOException, ServletException {
        if (!tipoRegistroPer.equals("INGRESO")) {
            enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }

        // Registrar nueva persona
        Persona persona = new Persona();
        persona.setRut(rutPer);
        persona.setNombre(nombrePer);
        PersonaDAO.registrar(persona);

        // Registrar el movimiento
        Registro registro = crearRegistro(rutPer, usuario.getIdUsuario(), fechaPer, tipoRegistroPer, horaPer);
        RegistroDAO.registrar(registro);
        response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }

    private Registro crearRegistro(String rut, int idUsuario, String fecha, String tipoRegistro, String hora) {
        Registro registro = new Registro();
        registro.setRut(rut);
        registro.setIdUsuario(idUsuario);
        registro.setFecha(Date.valueOf(fecha));
        registro.setTipoRegistro(tipoRegistro);
        registro.setHora(hora);
        return registro;
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws IOException, ServletException {
        request.setAttribute("errorLogin", mensaje);
        request.getRequestDispatcher("JSP/registrar_entrada_salida.jsp").forward(request, response);
    }
    private void listarRegistros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener datos necesarios
        List<RegistroPersona> registros = obtenerTodosLosRegistros();
        Map<String, Object> metricas = obtenerMetricasDelSistema();
        String fechaFormateada = obtenerFechaActualFormateada();

        // 2. Configurar atributos para la vista
        configurarAtributosVista(request, registros, metricas, fechaFormateada);

        // 3. Redirigir a la vista JSP
        redirigirAVista(request, response, "JSP/ver_registros.jsp");
    }

// Métodos auxiliares

    private List<RegistroPersona> obtenerTodosLosRegistros() {
        registroDAO registroDAO = new registroDAO();
        return registroDAO.obtenerRegistros();
    }

    private Map<String, Object> obtenerMetricasDelSistema() {
        ReporteDAO reporteDAO = new ReporteDAO();
        Map<String, Object> metricas = new HashMap<>();

        metricas.put("porcentajeAsistencia", reporteDAO.obtenerPorcentajeAsistenciaHoy());
        metricas.put("totalPersonas", reporteDAO.personasSistema());
        metricas.put("totalUsuarios", reporteDAO.usuariosSistema());
        metricas.put("personaDependencias", reporteDAO.dependenciasSistema());

        return metricas;
    }

    private String obtenerFechaActualFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"));
        return LocalDate.now().format(formatter);
    }

    private void configurarAtributosVista(HttpServletRequest request,
                                          List<RegistroPersona> registros,
                                          Map<String, Object> metricas,
                                          String fechaFormateada) {
        request.setAttribute("listaRegistros", registros);
        request.setAttribute("porcentajeAsistencia", metricas.get("porcentajeAsistencia"));
        request.setAttribute("totalPersonas", metricas.get("totalPersonas"));
        request.setAttribute("totalUsuarios", metricas.get("totalUsuarios"));
        request.setAttribute("personaDependencias", metricas.get("personaDependencias"));
        request.setAttribute("fechaActual", fechaFormateada);
    }

    private void redirigirAVista(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String vista)
            throws ServletException, IOException {
        request.getRequestDispatcher(vista).forward(request, response);
    }
}
