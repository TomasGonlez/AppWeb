package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportarEXCELServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los registros de la sesión
        List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");

        // Validar que existan registros
        if (registros == null || registros.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay datos para exportar");
            return;
        }

        // Configurar headers de respuesta ANTES de crear el workbook
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte_registros.xlsx\"");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        Workbook workbook = null;
        OutputStream outputStream = null;

        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Registros");

            // ===== CREAR ESTILOS =====

            // Fuente para datos
            Font dataFont = workbook.createFont();
            dataFont.setFontName("Arial");
            dataFont.setFontHeightInPoints((short) 10);

            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Bordes para encabezados
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Estilo base para datos con fuente
            CellStyle baseDataStyle = workbook.createCellStyle();
            baseDataStyle.setFont(dataFont);
            baseDataStyle.setBorderTop(BorderStyle.THIN);
            baseDataStyle.setBorderBottom(BorderStyle.THIN);
            baseDataStyle.setBorderLeft(BorderStyle.THIN);
            baseDataStyle.setBorderRight(BorderStyle.THIN);

            // Agregar padding a las celdas
            baseDataStyle.setIndention((short) 1); // Padding horizontal
            baseDataStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centrar verticalmente



            // Estilo para INGRESOS (verde claro)
            XSSFCellStyle ingresoStyle = (XSSFCellStyle) workbook.createCellStyle();
            ingresoStyle.cloneStyleFrom(baseDataStyle); // Clonar estilo base
            // Verde claro RGB: 200, 255, 200
            ingresoStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 200, (byte) 255, (byte) 200}, null));
            ingresoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Estilo para SALIDAS (rojo claro)
            XSSFCellStyle salidaStyle = (XSSFCellStyle) workbook.createCellStyle();
            salidaStyle.cloneStyleFrom(baseDataStyle); // Clonar estilo base
            // Rojo claro RGB: 255, 200, 200
            salidaStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 255, (byte) 200, (byte) 200}, null));
            salidaStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // ===== CREAR ENCABEZADOS =====
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"RUT", "Nombre", "Tipo Registro", "Fecha", "Hora"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // ===== LLENAR DATOS =====
            int rowNum = 1;
            for (RegistroPersona registro : registros) {
                Row row = sheet.createRow(rowNum);

                // Crear y llenar celdas
                Cell cellRut = row.createCell(0);
                cellRut.setCellValue(registro.getRut() != null ? registro.getRut() : "");

                Cell cellNombre = row.createCell(1);
                cellNombre.setCellValue(registro.getNombre() != null ? registro.getNombre() : "");

                Cell cellTipo = row.createCell(2);
                cellTipo.setCellValue(registro.getTipoRegistro() != null ? registro.getTipoRegistro() : "");

                Cell cellFecha = row.createCell(3);
                cellFecha.setCellValue(registro.getFecha() != null ? registro.getFecha().toString() : "");

                Cell cellHora = row.createCell(4);
                cellHora.setCellValue(registro.getHora() != null ? registro.getHora() : "");

                // Aplicar estilos según el tipo de registro
                CellStyle estiloAplicar = baseDataStyle;

                if (registro.getTipoRegistro() != null) {
                    String tipoRegistro = registro.getTipoRegistro().trim().toUpperCase();

                    if (tipoRegistro.equals("INGRESO")) {
                        estiloAplicar = ingresoStyle;
                    } else if (tipoRegistro.equals("SALIDA")) {
                        estiloAplicar = salidaStyle;
                    }
                }

                // Aplicar el estilo a todas las celdas de la fila
                cellRut.setCellStyle(estiloAplicar);
                cellNombre.setCellStyle(estiloAplicar);
                cellTipo.setCellStyle(estiloAplicar);
                cellFecha.setCellStyle(estiloAplicar);
                cellHora.setCellStyle(estiloAplicar);

                rowNum++;
            }

            // Autoajustar el ancho de las columnas y altura de filas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
                // Añadir un poco de padding extra
                int currentWidth = sheet.getColumnWidth(i);
                sheet.setColumnWidth(i, currentWidth + 1000);
            }
            // Aumentar la altura de todas las filas para más padding vertical
            for (int i = 0; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    row.setHeightInPoints(25); // Altura mayor (por defecto es ~15)
                }
            }

            // Obtener el OutputStream y escribir el archivo
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            // Si hay error, enviar respuesta de error
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error al generar el archivo Excel: " + e.getMessage());
            }
        } finally {
            // IMPORTANTE: Cerrar recursos en el orden correcto
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}