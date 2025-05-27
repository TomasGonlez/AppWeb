package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.personaDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.DAO.reporteDAO;
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
    /**private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Registro tempRegistro = new Registro();

        //Capturo los datos del fronted y los almaceno en variables
        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");
        String tipoRegistroPer = request.getParameter("tipoRegistro");
        String fechaPer = request.getParameter("fechaPersona");
        String horaPer = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));

        //Valida existencia de rut
        boolean validar = PersonaDAO.buscarRut(rutPer);
        //System.out.println("El rut "+ rutPer+" esta validado?: "+validar);

        //Verificar nombre asociado al rut
        boolean verificar = PersonaDAO.buscarNombre(nombrePer,rutPer);
        //System.out.println("El nombre "+ nombrePer+" esta verificado?: "+verificar);

        //


        // ✅ Recuperar el usuario logueado desde la sesión
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("JSP/login2.jsp");
            return;
        }
        int idUsuario = usuario.getIdUsuario(); // <-- Aquí está el valor correcto
        System.out.println("El id de usuario es: " + idUsuario);
        System.out.println("El rut es: " + rutPer);


        if (validar) {
            if(verificar) {
                // Validar alternancia con el último registro general
                String ultimoTipo = RegistroDAO.obtenerUltimoTipoRegistroGeneral(rutPer);

                // Validar alternancia global
                if (ultimoTipo == null && !tipoRegistroPer.equals("INGRESO")) {
                    request.setAttribute("errorLogin", "El primer registro debe ser un INGRESO.");
                    request.getRequestDispatcher("JSP/resgistrar_entrada_salida.jsp").forward(request, response);
                    return;
                }
                if (ultimoTipo != null && ultimoTipo.equals(tipoRegistroPer)) {
                    request.setAttribute("errorLogin", "No puedes registrar dos '" + tipoRegistroPer + "' consecutivos. Debes alternar entre INGRESO y SALIDA.");
                    request.getRequestDispatcher("JSP/resgistrar_entrada_salida.jsp").forward(request, response);
                    return;
                }

                tempRegistro.setRut(rutPer);
                tempRegistro.setIdUsuario(usuario.getIdUsuario());
                tempRegistro.setFecha(Date.valueOf(fechaPer));
                tempRegistro.setTipoRegistro(tipoRegistroPer);
                tempRegistro.setHora(horaPer);
                RegistroDAO.registrar(tempRegistro);
                response.sendRedirect("JSP/resgistrar_entrada_salida.jsp");
            }else{
                // El nombre que se captura no es el mismo que hay en la base de datos
                request.setAttribute("errorLogin", "El nombre ingresado no coincide con el rut del Sistema");
                request.getRequestDispatcher("JSP/resgistrar_entrada_salida.jsp").forward(request, response);
            }
        }else{
            if (!tipoRegistroPer.equals("INGRESO")) {
                request.setAttribute("errorLogin", "El primer registro debe ser un INGRESO.");
                request.getRequestDispatcher("JSP/resgistrar_entrada_salida.jsp").forward(request, response);
                return;
            }
            Persona tempPersona = new Persona();
            tempPersona.setRut(rutPer);
            tempPersona.setNombre(nombrePer);
            PersonaDAO.registrar(tempPersona);
            tempRegistro.setRut(tempPersona.getRut());
            tempRegistro.setIdUsuario(usuario.getIdUsuario());
            tempRegistro.setFecha(Date.valueOf(fechaPer));
            tempRegistro.setTipoRegistro(tipoRegistroPer);
            tempRegistro.setHora(horaPer);
            RegistroDAO.registrar(tempRegistro);
            response.sendRedirect("JSP/resgistrar_entrada_salida.jsp");
        }
    }**/
    private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 1. Validar sesión de usuario
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("JSP/login2.jsp");
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
                    "No puedes registrar dos '" + tipoRegistroPer + "' consecutivos. Debes alternar entre INGRESO y SALIDA.");
            return;
        }

        // Registrar el movimiento
        Registro registro = crearRegistro(rutPer, usuario.getIdUsuario(), fechaPer, tipoRegistroPer, horaPer);
        RegistroDAO.registrar(registro);
        response.sendRedirect("JSP/resgistrar_entrada_salida.jsp");
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
        response.sendRedirect("JSP/resgistrar_entrada_salida.jsp");
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
        request.getRequestDispatcher("JSP/resgistrar_entrada_salida.jsp").forward(request, response);
    }

    /**private void listarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear instancias
        registroDAO registroDAO = new registroDAO();
        reporteDAO reporte = new reporteDAO();


        List<RegistroPersona> lista = registroDAO.obtenerRegistros();
        double porcentajeAsistencia = reporte.obtenerPorcentajeAsistenciaHoy();
        int totalPersonas = reporte.personasSistema();
        int totalUsuarios = reporte.usuariosSistema();

        int personaDependencias = reporte.dependenciasSistema();

        // Nueva implementación con formato en español
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"));
        String fechaActual = LocalDate.now().format(formatter);

        request.setAttribute("listaRegistros", lista);
        request.setAttribute("porcentajeAsistencia", porcentajeAsistencia);
        request.setAttribute("totalPersonas", totalPersonas);
        request.setAttribute("totalUsuarios", totalUsuarios);
        request.setAttribute("fechaActual", fechaActual);
        request.setAttribute("personaDependencias", personaDependencias);
        // Redirige al JSP
        request.getRequestDispatcher("JSP/ver_registros.jsp").forward(request, response);
    }**/
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
        reporteDAO reporteDAO = new reporteDAO();
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
