package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;


import com.example.appweb.SERVICIO.ReporteService;
import com.example.appweb.UTIL.ValidadorFechas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReporteServlet extends HttpServlet {
    private final ReporteService service = new ReporteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // LIMPIAR SIEMPRE LOS DATOS AL ENTRAR AL MÓDULO
        request.getSession().removeAttribute("registros");
        request.getSession().removeAttribute("registrosDependencia");
        forward(request, response, "/JSP/reportes.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            // Acción no proporcionada, redirige a página de error
            response.sendRedirect("JSP/error.jsp");
            return;
        }
        switch (accion) {
            case "reporteFechas":
                procesarReporteFechas(request, response);
                break;
            case "reporteDependencias":
                procesarReporteDependencias(request, response);
                break;
            default:
                response.sendRedirect("JSP/error.jsp");
                break;
        }
    }

    private void procesarReporteFechas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String desde = request.getParameter("desde");
        String hasta = request.getParameter("hasta");

        if (desde == null || hasta == null || desde.isEmpty() || hasta.isEmpty()) {
            enviarError(request, response, "Debe ingresar ambas fechas.");
            return;
        }
        try{
            ValidadorFechas.validarRangoFechas(desde, hasta);
        }catch (IllegalArgumentException e){
            enviarError(request, response, e.getMessage());
            return;
        }
        try {
            // 1. Obtener nuevos datos para reporte por fechas
            List<RegistroPersona> registros = service.obtenerPorFechas(desde, hasta);


            // 2. GUARDAR REPORTE SIN AFECTAR EL OTRO
            request.getSession().setAttribute("registros", registros);

            // 3. Guardar fechas en request para mostrar en la vista
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);

            // 4. Redirigir
            forward(request, response, "/JSP/reportes.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Error al obtener registros por fecha.");
        }
    }

    private void procesarReporteDependencias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Obtener nuevos datos para reporte por dependencias
            List<RegistroPersona> registros = service.obtenerPorDependencias();

            // 2. GUARDAR REPORTE SIN AFECTAR EL OTRO
            request.getSession().setAttribute("registrosDependencia", registros);

            // 3. Redirigir
            forward(request, response, "/JSP/reportes.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Error al obtener registros por dependencia.");
        }
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("errorLogin", mensaje);
        request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
    }
    private void forward(HttpServletRequest request, HttpServletResponse response, String ruta) throws ServletException, IOException {
        request.getRequestDispatcher(ruta).forward(request, response);
    }
}