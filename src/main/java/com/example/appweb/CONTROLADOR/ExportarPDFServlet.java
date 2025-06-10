package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportarPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");

        // Validar que existan registros
        if (registros == null || registros.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay datos para exportar");
            return;
        }

        // Configurar headers de respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte_registros.pdf\"");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        Document document = null;
        OutputStream outputStream = null;

        try {
            document = new Document(PageSize.A4, 36, 36, 36, 36); // Márgenes más grandes
            outputStream = response.getOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // ===== TÍTULO =====
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph title = new Paragraph("Reporte de Registros", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20); // Espacio después del título
            document.add(title);

            // ===== CREAR TABLA CON PADDING =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 3, 2, 2, 2});
            table.setSpacingBefore(10); // Espacio antes de la tabla
            table.setSpacingAfter(10);  // Espacio después de la tabla

            // ===== DEFINIR FUENTES =====
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // ===== CREAR ENCABEZADOS CON ESTILO =====
            String[] columnas = {"RUT", "Nombre", "Tipo Registro", "Fecha", "Hora"};

            for (String columna : columnas) {
                PdfPCell headerCell = new PdfPCell(new Phrase(columna, headerFont));
                headerCell.setBackgroundColor(new BaseColor(51, 102, 255)); // Azul claro
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // PADDING PARA ENCABEZADOS
                headerCell.setPadding(12); // Padding general
                headerCell.setPaddingTop(15);
                headerCell.setPaddingBottom(15);
                headerCell.setPaddingLeft(10);
                headerCell.setPaddingRight(10);

                headerCell.setBorder(Rectangle.BOX);
                headerCell.setBorderColor(BaseColor.GRAY);

                table.addCell(headerCell);
            }

            // ===== LLENAR DATOS CON COLORES Y PADDING =====
            boolean isEvenRow = false;

            for (RegistroPersona registro : registros) {
                // Determinar color de fondo según tipo de registro
                BaseColor backgroundColor;
                if (registro.getTipoRegistro() != null) {
                    String tipoRegistro = registro.getTipoRegistro().trim().toUpperCase();
                    if (tipoRegistro.equals("INGRESO")) {
                        backgroundColor = new BaseColor(200, 255, 200); // Verde claro
                    } else if (tipoRegistro.equals("SALIDA")) {
                        backgroundColor = new BaseColor(255, 200, 200); // Rojo claro
                    } else {
                        backgroundColor = isEvenRow ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                    }
                } else {
                    backgroundColor = isEvenRow ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                }

                // Crear celdas con los datos
                String[] datos = {
                        registro.getRut() != null ? registro.getRut() : "",
                        registro.getNombre() != null ? registro.getNombre() : "",
                        registro.getTipoRegistro() != null ? registro.getTipoRegistro() : "",
                        registro.getFecha() != null ? registro.getFecha().toString() : "",
                        registro.getHora() != null ? registro.getHora() : ""
                };

                for (int i = 0; i < datos.length; i++) {
                    PdfPCell dataCell = new PdfPCell(new Phrase(datos[i], dataFont));
                    dataCell.setBackgroundColor(backgroundColor);

                    // Alineación según la columna
                    if (i == 0 || i == 3 || i == 4) { // RUT, Fecha, Hora - centrado
                        dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    } else { // Nombre, Tipo - izquierda
                        dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                    dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // PADDING PARA DATOS - MÁS ESPACIO
                    dataCell.setPadding(8); // Padding general
                    dataCell.setPaddingTop(12);    // Más espacio arriba
                    dataCell.setPaddingBottom(12); // Más espacio abajo
                    dataCell.setPaddingLeft(8);    // Espacio izquierda
                    dataCell.setPaddingRight(8);   // Espacio derecha

                    // Bordes
                    dataCell.setBorder(Rectangle.BOX);
                    dataCell.setBorderWidth(0.5f);
                    dataCell.setBorderColor(BaseColor.GRAY);

                    table.addCell(dataCell);
                }

                isEvenRow = !isEvenRow; // Alternar color para filas sin tipo específico
            }

            // Agregar la tabla al documento
            document.add(table);

            // ===== PIE DE PÁGINA =====
            Paragraph footer = new Paragraph("Total de registros: " + registros.size(),
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setSpacingBefore(20);
            document.add(footer);

        } catch (DocumentException e) {
            e.printStackTrace();
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error al generar el archivo PDF: " + e.getMessage());
            }
        } finally {
            // Cerrar recursos
            try {
                if (document != null && document.isOpen()) {
                    document.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}