package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.personaDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

public class RegistroServlet extends HttpServlet {

    private personaDAO PersonaDAO;
    private registroDAO RegistroDAO;

    @Override
    public void init() throws ServletException{
        PersonaDAO = new personaDAO();
        RegistroDAO = new registroDAO();

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
        //Datos para la tabla Persona
        String rutPer = request.getParameter("rutPersona");
        String nombrePer = request.getParameter("nombrePersona");

        //Datos para la tabla registro
        String tipoRegistroPer = request.getParameter("tipoRegistro");
        String fechaPer = request.getParameter("fechaPersona");

        // ✅ Recuperar el usuario logueado desde la sesión
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("JSP/login2.jsp");
            return;
        }
        int idUsuario = usuario.getIdUsuario(); // <-- Aquí está el valor correcto
        System.out.println("El id de usuario es: " + idUsuario);
        try {
            Persona nuevaPersona = new Persona();
            nuevaPersona.setRut(rutPer);
            nuevaPersona.setNombre(nombrePer);

            boolean buscarPersona = PersonaDAO.buscarRut(rutPer);

            if (buscarPersona) {
                //Si entra a este if significa que el rut que se esta ingresando en registrar entrada/salida ya existe en la tabla PERSONA
                // por ende no debemos crear el registro en la tabla persona, solo crear un nuevo registro en tabla registro
                Registro nuevoRegistro = new Registro();
                nuevoRegistro.setRut(rutPer);
                nuevoRegistro.setIdUsuario(idUsuario);
                nuevoRegistro.setFechaHora(Date.valueOf(fechaPer));
                nuevoRegistro.setTipoRegistro(tipoRegistroPer);
                RegistroDAO.registrar(nuevoRegistro);
                response.sendRedirect("JSP/login2.jsp");
            }else{
                //Se crea el registro en la tabla persona (rut y nombre)
                boolean exitoPersona = PersonaDAO.registrar(nuevaPersona);
                Registro nuevoRegistro = new Registro();
                nuevoRegistro.setRut(rutPer);
                nuevoRegistro.setIdUsuario(idUsuario);
                nuevoRegistro.setFechaHora(Date.valueOf(fechaPer));
                nuevoRegistro.setTipoRegistro(tipoRegistroPer);
                RegistroDAO.registrar(nuevoRegistro);
                response.sendRedirect("JSP/login2.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
}
