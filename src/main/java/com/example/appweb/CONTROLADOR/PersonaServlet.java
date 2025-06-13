package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.PersonaService;
import com.example.appweb.UTIL.Errores;
import com.example.appweb.UTIL.RegistroUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class PersonaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;
        if (usuario == null) {
            response.sendRedirect("JSP/login.jsp");
            return;
        }
        String accion = request.getParameter("accion");
        if ("registrar".equals(accion)) {
            registrarPersona(request, response);
        }else{
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonaService personaService = new PersonaService();
        PersonaDAO DAO = new PersonaDAO();

        String rut = request.getParameter("rutPersona");
        String nombre = request.getParameter("nombrePersona");

        if(personaService.validarRut(rut)){
            Errores.enviarErrorRegistrarPersona(request, response,"El rut ingresado ya existe");
            return;
        }
        Persona persona = personaService.crearPersona(rut, nombre);
        DAO.registrar(persona);
        response.sendRedirect("JSP/registrar_empleado.jsp");
    }
}
