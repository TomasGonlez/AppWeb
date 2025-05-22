package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.personaDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.DAO.reporteDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
        Registro tempRegistro = new Registro();

        //Capturo los datos del fronted y los almaceno en variables
        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");
        String tipoRegistroPer = request.getParameter("tipoRegistro");
        String fechaPer = request.getParameter("fechaPersona");
        String horaPer = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));

        //Validar rut que se ingresa exista en la tabla
        boolean validar = PersonaDAO.buscarRut(rutPer);
        //System.out.println("El rut "+ rutPer+" esta validado?: "+validar);

        //Si el validar es true significa que debo verificar que el nombre que se captura sea el mismo que en la tabla
        boolean verificar = PersonaDAO.buscarNombre(nombrePer,rutPer);
        //System.out.println("El nombre "+ nombrePer+" esta verificado?: "+verificar);


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
    }

    private void listarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }
}
