package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UsuarioServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "registrar":
                registrarUsuario(request, response);
                break;
            case "login":
                loginUsuario(request, response);
                break;
            default:
                response.sendRedirect("JSP/error.jsp");
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String origenFormulario = request.getParameter("origen");
        try {
            Usuario nuevoUsuario = usuarioService.construirUsuarioDesdeRequest(request);

            if (usuarioService.existeNombreUsuario(nuevoUsuario.getNombreUser())) {
                enviarError(request, response, "El nombre de usuario ya existe.",origenFormulario);
                return;
            }
            boolean exito = usuarioService.registrarUsuario(nuevoUsuario);

            if (exito) {
                if("SESSION".equals(origenFormulario)) {
                    response.sendRedirect("JSP/crearUsuario.jsp");
                } else if ("NO_SESSION".equals(origenFormulario)) {
                    request.setAttribute("exitoRegistro", "Usuario registrado con éxito");
                    request.getRequestDispatcher("JSP/login.jsp").forward(request, response);
                }else{
                    enviarError(request, response, "Origen del formulario no reconocido.",origenFormulario);
                    //System.out.println("Origen del formulario no reconocido");
                }
            } else {
                enviarError(request, response, "Error al registrar el usuario.",origenFormulario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Se produjo un error inesperado al registrar el usuario.",origenFormulario);
        }
    }

    private void loginUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasenaUsuario");

            Usuario usuario = usuarioService.loginUsuario(nombre, contrasena);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect(request.getContextPath() + "/RegistroServlet?accion=listarRegistros");
            } else {
                enviarErrorLogin(request, response, "Credenciales incorrectas. Inténtalo nuevamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarErrorLogin(request, response, "Error inesperado durante el login.");
        }
    }
    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje, String origenForm)
            throws ServletException, IOException {

        request.setAttribute("errorRegistroUsuario", mensaje);
        if("SESSION".equals(origenForm)) {
            request.getRequestDispatcher("/JSP/crearUsuario.jsp").forward(request, response);
        }else if("NO_SESSION".equals(origenForm)) {
            request.getRequestDispatcher("/JSP/crearUsuario_NO_SESSION.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/JSP/login.jsp").forward(request, response);
        }
    }
    private void enviarErrorLogin(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("errorLogin", mensaje);
        request.getRequestDispatcher("/JSP/login.jsp").forward(request, response);
    }
}
