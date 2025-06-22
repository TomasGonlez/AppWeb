package com.example.appweb.SERVICIO;

import com.example.appweb.MODELO.RegistroPersona;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class ExportServicePDF {

    public void generarPDF(List<RegistroPersona> registros, HttpServletResponse response) throws Exception {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        try (OutputStream outputStream = response.getOutputStream()) {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            agregarTitulo(document);
            agregarTabla(document, registros);
            document.close();
        }
    }

    private void agregarTitulo(Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Reporte de Registros", font);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);
    }

    private void agregarTabla(Document document, List<RegistroPersona> registros) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 3, 2, 2, 2});
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        agregarEncabezados(table);
        llenarDatos(table, registros);
        document.add(table);
    }

    private void agregarEncabezados(PdfPTable table) {
        String[] columnas = {"RUT", "Nombre", "Tipo Registro", "Fecha", "Hora"};
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

        for (String columna : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(columna, headerFont));
            cell.setBackgroundColor(new BaseColor(51, 102, 255));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(12);
            cell.setBorderColor(BaseColor.GRAY);
            table.addCell(cell);
        }
    }

    private void llenarDatos(PdfPTable table, List<RegistroPersona> registros) {
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        boolean isEvenRow = false;

        for (RegistroPersona registro : registros) {
            BaseColor backgroundColor = determinarColorFondo(registro, isEvenRow);
            String[] datos = obtenerDatosRegistro(registro);

            for (int i = 0; i < datos.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(datos[i], dataFont));
                cell.setBackgroundColor(backgroundColor);
                cell.setHorizontalAlignment(i == 0 || i == 3 || i == 4 ? Element.ALIGN_CENTER : Element.ALIGN_LEFT);
                cell.setPadding(8);
                cell.setBorderWidth(0.5f);
                table.addCell(cell);
            }
            isEvenRow = !isEvenRow;
        }
    }

    private BaseColor determinarColorFondo(RegistroPersona registro, boolean isEvenRow) {
        if (registro.getTipoRegistro() == null) {
            return isEvenRow ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
        }
        return switch (registro.getTipoRegistro().trim().toUpperCase()) {
            case "INGRESO" -> new BaseColor(200, 255, 200);
            case "SALIDA" -> new BaseColor(255, 200, 200);
            default -> isEvenRow ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
        };
    }

    private String[] obtenerDatosRegistro(RegistroPersona registro) {
        return new String[]{
                registro.getRut() != null ? registro.getRut() : "",
                registro.getNombre() != null ? registro.getNombre() : "",
                registro.getTipoRegistro() != null ? registro.getTipoRegistro() : "",
                registro.getFecha() != null ? registro.getFechaFormateada().toString() : "",
                registro.getHora() != null ? registro.getHora() : ""
        };
    }
}