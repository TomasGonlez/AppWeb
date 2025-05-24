package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ExportarEXCELServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte registros.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Registros");

        Row header = sheet.createRow(0);
        String[] columnas = {"RUT", "Nombre", "Tipo Registro", "Fecha", "Hora"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        if (registros != null) {
            int fila = 1;
            for (RegistroPersona r : registros) {
                Row row = sheet.createRow(fila++);
                row.createCell(0).setCellValue(r.getRut());
                row.createCell(1).setCellValue(r.getNombre());
                row.createCell(2).setCellValue(r.getTipoRegistro());
                row.createCell(3).setCellValue(r.getFecha().toString());
                row.createCell(4).setCellValue(r.getHora());
            }
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
