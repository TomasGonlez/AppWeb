package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.SERVICIO.ExportServiceEXCEL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ExportarEXCELServlet extends HttpServlet {

    private ExportServiceEXCEL excelService;

    public void init() throws ServletException {
        // Obtener el servicio del contexto de la aplicación
        excelService = (ExportServiceEXCEL) getServletContext().getAttribute("exportServiceEXCEL");
        if (excelService == null) {
            throw new ServletException("No se pudo inicializar el servicio de exportación Excel");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");

            if (registros == null || registros.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay datos para exportar");
                return;
            }

            configurarRespuestaExcel(response);
            excelService.generarExcel(registros, response);

        } catch (Exception e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar Excel: " + e.getMessage());
            }
        }
    }

    private void configurarRespuestaExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.xlsx\"");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }
}