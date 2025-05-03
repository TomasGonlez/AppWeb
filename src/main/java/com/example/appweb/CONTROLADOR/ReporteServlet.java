package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.reporteDAO;
import com.example.appweb.MODELO.Registro;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReporteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String desde = request.getParameter("desde");
        String hasta = request.getParameter("hasta");

        if (desde == null || hasta == null || desde.isEmpty() || hasta.isEmpty()) {
            request.setAttribute("error", "Debe ingresar ambas fechas.");
            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
            return;
        }

        try {
            reporteDAO dao = new reporteDAO();
            List<Registro> registros = dao.obtenerRegistrosPorFecha(desde, hasta);

            request.setAttribute("registros", registros);
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);

            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener registros.");
            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        }
    }
}