package com.example.appweb.SERVICIO;

import com.example.appweb.MODELO.RegistroPersona;
import jakarta.servlet.http.HttpServlet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class ExportServiceEXCEL extends HttpServlet {

    public void generarExcel(List<RegistroPersona> registros, HttpServletResponse response) throws Exception {
        try (Workbook workbook = new XSSFWorkbook();
             OutputStream outputStream = response.getOutputStream()) {

            Sheet sheet = workbook.createSheet("Registros");
            configurarEstilos(workbook); // Estilos reutilizables

            crearCabeceras(workbook, sheet);
            llenarDatos(workbook, sheet, registros);

            workbook.write(outputStream);
        }
    }

    private void configurarEstilos(Workbook workbook) {
        // Estilos pueden declararse aquí si son reutilizables
    }

    private void crearCabeceras(Workbook workbook, Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"RUT", "Nombre", "Tipo Registro", "Fecha", "Hora"};

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void llenarDatos(Workbook workbook, Sheet sheet, List<RegistroPersona> registros) {
        // Colores corregidos (sin llaves extras)
        CellStyle estiloIngreso = crearEstiloXSSF(workbook,new XSSFColor(new byte[]{(byte) 200, (byte) 255, (byte) 200}, null)); // Verde claro
        CellStyle estiloSalida = crearEstiloXSSF(workbook,new XSSFColor(new byte[]{(byte) 255, (byte) 200, (byte) 200}, null)); // Rojo claro
        CellStyle estiloBase = crearEstiloIndexed(workbook, IndexedColors.WHITE);

        int rowNum = 1;
        for (RegistroPersona registro : registros) {
            Row row = sheet.createRow(rowNum);

            CellStyle estiloAplicar = determinarEstilo(registro, estiloIngreso, estiloSalida, estiloBase);
            llenarFila(row, registro, estiloAplicar);

            rowNum++;
        }

        // Autoajuste corregido
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    // Para IndexedColors
    private CellStyle crearEstiloIndexed(Workbook workbook, IndexedColors color) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    // Para XSSFColor
    private CellStyle crearEstiloXSSF(Workbook workbook, XSSFColor color) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle determinarEstilo(RegistroPersona registro, CellStyle ingreso, CellStyle salida, CellStyle base) {
        if (registro.getTipoRegistro() == null) return base;
        return switch (registro.getTipoRegistro().trim().toUpperCase()) {
            case "INGRESO" -> ingreso;
            case "SALIDA" -> salida;
            default -> base;
        };
    }

    private void llenarFila(Row row, RegistroPersona registro, CellStyle estilo) {
        String[] datos = {
                registro.getRut() != null ? registro.getRut() : "",
                registro.getNombre() != null ? registro.getNombre() : "",
                registro.getTipoRegistro() != null ? registro.getTipoRegistro() : "",
                registro.getFecha() != null ? registro.getFechaFormateada().toString() : "",
                registro.getHora() != null ? registro.getHora() : ""
        };

        for (int i = 0; i < datos.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(datos[i]);
            cell.setCellStyle(estilo);
        }
    }
}