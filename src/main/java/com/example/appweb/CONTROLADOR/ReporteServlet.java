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
    private ReporteService service;

    // Parámetros del request
    private static final String PARAM_ACCION = "accion";
    private static final String PARAM_DESDE = "desde";
    private static final String PARAM_HASTA = "hasta";

    // Atributos del request y sesión
    private static final String ATTR_REGISTROS_FECHAS = "registros";
    private static final String ATTR_REGISTROS_DEPENDENCIA = "registrosDependencia";
    private static final String ATTR_ERROR_LOGIN = "errorLogin";
    private static final String ATTR_DESDE = "desde";
    private static final String ATTR_HASTA = "hasta";

    // Acciones esperadas
    private static final String ACCION_REPORTE_FECHAS = "reporteFechas";
    private static final String ACCION_REPORTE_DEPENDENCIAS = "reporteDependencias";

    // Rutas JSP
    private static final String VISTA_REPORTES = "/JSP/reportes.jsp";
    private static final String VISTA_ERROR = "JSP/error.jsp";

    @Override
    public void init() {
        this.service = (ReporteService) getServletContext().getAttribute("reporteService"); // ✅ Obtenido del contexto
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Limpiar atributos de sesión al entrar al módulo
        request.getSession().removeAttribute(ATTR_REGISTROS_FECHAS);
        request.getSession().removeAttribute(ATTR_REGISTROS_DEPENDENCIA);
        forward(request, response, VISTA_REPORTES);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter(PARAM_ACCION);

        if (accion == null) {
            response.sendRedirect(VISTA_ERROR);
            return;
        }

        switch (accion) {
            case ACCION_REPORTE_FECHAS:
                procesarReporteFechas(request, response);
                break;
            case ACCION_REPORTE_DEPENDENCIAS:
                procesarReporteDependencias(request, response);
                break;
            default:
                response.sendRedirect(VISTA_ERROR);
                break;
        }
    }

    private void procesarReporteFechas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String desde = request.getParameter(PARAM_DESDE);
        String hasta = request.getParameter(PARAM_HASTA);

        if (desde == null || hasta == null || desde.isEmpty() || hasta.isEmpty()) {
            enviarError(request, response, "Debe ingresar ambas fechas.");
            return;
        }

        try {
            ValidadorFechas.validarRangoFechas(desde, hasta);
        } catch (IllegalArgumentException e) {
            enviarError(request, response, e.getMessage());
            return;
        }

        try {
            List<RegistroPersona> registros = service.obtenerPorFechas(desde, hasta);
            request.getSession().setAttribute(ATTR_REGISTROS_FECHAS, registros);
            request.setAttribute(ATTR_DESDE, desde);
            request.setAttribute(ATTR_HASTA, hasta);
            forward(request, response, VISTA_REPORTES);
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Error al obtener registros por fecha.");
        }
    }

    private void procesarReporteDependencias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<RegistroPersona> registros = service.obtenerPorDependencias();
            request.getSession().setAttribute(ATTR_REGISTROS_DEPENDENCIA, registros);
            forward(request, response, VISTA_REPORTES);
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Error al obtener registros por dependencia.");
        }
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute(ATTR_ERROR_LOGIN, mensaje);
        forward(request, response, VISTA_REPORTES);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String ruta)
            throws ServletException, IOException {
        request.getRequestDispatcher(ruta).forward(request, response);
    }
}
