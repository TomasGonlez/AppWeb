package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.RegistroUtils;
import com.example.appweb.UTIL.RegistroUtils.*;
import com.example.appweb.UTIL.ValidadorFechas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class RegistroServlet extends HttpServlet {

    private PersonaDAO PersonaDAO;
    private RegistroDAO RegistroDAO;

    @Override
    public void init() throws ServletException{
        PersonaDAO = new PersonaDAO();
        RegistroDAO = new RegistroDAO();

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
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("JSP/login.jsp");
            return;
        }

        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");
        String tipoRegistroPer = request.getParameter("tipoRegistro");
        String fechaPer = request.getParameter("fechaPersona");

        try {
            ValidadorFechas.validarFechaNoFutura(fechaPer);
        } catch (IllegalArgumentException e) {
            RegistroUtils.enviarError(request, response, e.getMessage());
            return;
        }

        String horaPer = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));

        boolean rutValido = PersonaDAO.buscarRut(rutPer);
        boolean nombreVerificado = PersonaDAO.buscarNombre(nombrePer, rutPer);


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
        // 1. Validar coincidencia de nombre y RUT
        if (!nombreVerificado) {
            RegistroUtils.enviarError(request, response, "El nombre ingresado no coincide con el rut del Sistema");
            return;
        }

        // 2. Validar alternancia de tipos (INGRESO -> SALIDA -> INGRESO)
        String ultimoTipo = RegistroDAO.obtenerUltimoTipoRegistroGeneral(rutPer);

        if (ultimoTipo == null && !tipoRegistroPer.equals("INGRESO")) {
            RegistroUtils.enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }

        if (ultimoTipo != null && ultimoTipo.equals(tipoRegistroPer)) {
            RegistroUtils.enviarError(request, response,"No puedes registrar dos " + tipoRegistroPer + " consecutivos. Debes alternar entre INGRESO y SALIDA.");
            return;
        }

        // 3. [NUEVA VALIDACIÓN] Verificar coherencia de fechas para SALIDAS
        if (tipoRegistroPer.equals("SALIDA")) {
            try {
                // Convertir fecha del formulario a Date
                Date fechaSalida = Date.valueOf(fechaPer);

                // Obtener fecha del último INGRESO (CORRECCIÓN AQUÍ)
                Date ultimoIngreso = RegistroDAO.obtenerUltimaFechaIngreso(rutPer);

                // Validar existencia de INGRESO previo
                if (ultimoIngreso == null) {
                    RegistroUtils.enviarError(request, response, "No hay un INGRESO registrado para este usuario.");
                    return;
                }

                // Validar que SALIDA sea posterior al INGRESO
                if (fechaSalida.before(ultimoIngreso)) {
                    RegistroUtils.enviarError(request, response,
                            "La SALIDA no puede ser antes del último INGRESO (" + ultimoIngreso + ")");
                    return;
                }
            } catch (Exception e) {
                RegistroUtils.enviarError(request, response, "Error al validar fechas: " + e.getMessage());
                return;
            }
        }

        // 4. Si pasa todas las validaciones, registrar en BD
        try {
            Registro registro = RegistroUtils.crearRegistro(rutPer, usuario.getIdUsuario(), fechaPer, tipoRegistroPer, horaPer);
            RegistroDAO.registrar(registro);
            response.sendRedirect("JSP/registrar_entrada_salida.jsp");
        } catch (Exception e) {
            RegistroUtils.enviarError(request, response, "Error al guardar el registro: " + e.getMessage());
        }
    }


    private void procesarNuevoRegistro(HttpServletRequest request, HttpServletResponse response,
                                       Usuario usuario, String rutPer, String nombrePer,
                                       String tipoRegistroPer, String fechaPer, String horaPer)
            throws IOException, ServletException {
        if (!tipoRegistroPer.equals("INGRESO")) {
            RegistroUtils.enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }

        // Registrar nueva persona
        Persona persona = new Persona();
        persona.setRut(rutPer);
        persona.setNombre(nombrePer);
        PersonaDAO.registrar(persona);

        // Registrar el movimiento
        Registro registro = RegistroUtils.crearRegistro(rutPer, usuario.getIdUsuario(), fechaPer, tipoRegistroPer, horaPer);
        RegistroDAO.registrar(registro);
        response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }


    private void listarRegistros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener datos necesarios
        List<RegistroPersona> registros = RegistroUtils.obtenerTodosLosRegistros();
        Map<String, Object> metricas = RegistroUtils.obtenerMetricasDelSistema();
        String fechaFormateada = RegistroUtils.obtenerFechaActualFormateada();

        // 2. Configurar atributos para la vista
        RegistroUtils.configurarAtributosVista(request, registros, metricas, fechaFormateada);

        // 3. Redirigir a la vista JSP
        RegistroUtils.redirigirAVista(request, response, "JSP/ver_registros.jsp");
    }
}
