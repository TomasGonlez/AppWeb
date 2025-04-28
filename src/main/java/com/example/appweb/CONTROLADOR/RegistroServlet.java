package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.personaDAO;
import com.example.appweb.MODELO.Persona;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistroServlet extends HttpServlet {

    private personaDAO PersonaDAO;

    @Override
    public void init() throws ServletException{
        PersonaDAO = new personaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrarPersona(request, response);

        }else {
            response.sendRedirect("JSP/error.jsp");
        }
    }

    private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");

        try {
            Persona nuevaPersona = new Persona();
            nuevaPersona.setRut(rutPer);
            nuevaPersona.setNombre(nombrePer);

            boolean exito = PersonaDAO.registrar(nuevaPersona);

            if (exito) {
                response.sendRedirect("JSP/login2.jsp");

            } else {
                response.sendRedirect("JSP/error1.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
}
