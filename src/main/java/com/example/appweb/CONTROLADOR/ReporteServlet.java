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
            List<RegistroPersona> registros = service.obtenerPorFechas(desde, hasta);
            request.setAttribute("registros", registros);
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);
            request.getSession().setAttribute("registros", registros);
            forward(request, response, "/JSP/reportes.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Error al obtener registros por fecha.");
        }
    }

    private void procesarReporteDependencias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RegistroPersona> registros = service.obtenerPorDependencias();
            request.setAttribute("registrosDependencia", registros);
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