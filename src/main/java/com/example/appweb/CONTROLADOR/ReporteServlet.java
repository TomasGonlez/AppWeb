package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.reporteDAO;
import com.example.appweb.MODELO.RegistroPersona;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReporteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("listar".equals(accion)) {
            ObtenerRegistroFecha(request, response);
        } else {
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void ObtenerRegistroFecha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String desde = request.getParameter("desde");
        String hasta = request.getParameter("hasta");

        if (desde == null || hasta == null || desde.isEmpty() || hasta.isEmpty()) {
            request.setAttribute("error", "Debe ingresar ambas fechas.");
            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        }
        try{
            reporteDAO DAO = new reporteDAO();
            List<RegistroPersona> registrosLista = DAO.obtenerRegistrosPorFecha(desde, hasta);
            request.setAttribute("registros", registrosLista);
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);

            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener registros por fecha.");
            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        }

    }
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
            List<RegistroPersona> registros = dao.obtenerRegistrosPorFecha(desde, hasta);

            request.setAttribute("registros", registros);
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);

            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener registros.");
            request.getRequestDispatcher("/JSP/reportes.jsp").forward(request, response);
        }
    }*/
}