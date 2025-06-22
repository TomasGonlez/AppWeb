package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.SERVICIO.ExportServicePDF;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ExportarPDFServlet extends HttpServlet {

    private ExportServicePDF pdfService;

    @Override
    public void init() throws ServletException {
        // Obtener el servicio del contexto de la aplicación
        pdfService = (ExportServicePDF) getServletContext().getAttribute("exportServicePDF");
        if (pdfService == null) {
            throw new ServletException("No se pudo inicializar el servicio de exportación PDF");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");
            validarRegistros(registros, response);
            configurarRespuestaPDF(response);
            pdfService.generarPDF(registros, response);
        } catch (Exception e) {
            manejarError(response, "Error al generar PDF: " + e.getMessage());
        }
    }

    private void validarRegistros(List<RegistroPersona> registros, HttpServletResponse response) throws IOException {
        if (registros == null || registros.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay datos para exportar");
            throw new IllegalArgumentException("Registros vacíos");
        }
    }

    private void configurarRespuestaPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\"");
    }

    private void manejarError(HttpServletResponse response, String mensaje) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}