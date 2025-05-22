package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.RegistroPersona;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ExportarPDFServlet")
public class ExportarPDFServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RegistroPersona> registros = (List<RegistroPersona>) request.getSession().getAttribute("registros");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_registros.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Reporte de Registros", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 3, 2, 2, 2});

            table.addCell("RUT");
            table.addCell("Nombre");
            table.addCell("Tipo Registro");
            table.addCell("Fecha");
            table.addCell("Hora");

            if (registros != null) {
                for (RegistroPersona r : registros) {
                    table.addCell(r.getRut());
                    table.addCell(r.getNombre());
                    table.addCell(r.getTipoRegistro());
                    table.addCell(r.getFecha().toString());
                    table.addCell(r.getHora());
                }
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
}
