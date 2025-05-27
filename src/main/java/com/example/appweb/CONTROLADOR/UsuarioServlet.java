package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.DAO.reporteDAO;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException{
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrarUsuario(request, response);
        } else if ("login".equals(accion)) {
            loginUsuario(request, response);
        } else {
            response.sendRedirect("JSP/error.jsp");
        }

    }
    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombreCompleto = request.getParameter("nombreCompletoUser");
        String correo = request.getParameter("correoUser");
        int numero = Integer.parseInt(request.getParameter("numeroUser"));
        String nombre = request.getParameter("nombreUser");
        String contrasena = request.getParameter("contrasena");


        String nombreUsuario = request.getParameter("nombreUser");
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.existeNombreUsuario(nombreUsuario)) {
            request.setAttribute("errorNombreUSER", "El nombre de usuario ya existe.");
            RequestDispatcher rd = request.getRequestDispatcher("JSP/crearUsuario.jsp");
            rd.forward(request,response);
            return;
        }

        try {
            LocalDate fecha = LocalDate.now();
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreCompletoUser(nombreCompleto);
            nuevoUsuario.setCorreoUser(correo);
            nuevoUsuario.setNumeroUser(numero);
            nuevoUsuario.setNombreUser(nombre);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setFechaCreacion(fecha);

            boolean exito = usuarioDAO.guardar(nuevoUsuario);

            if (exito) {
                // Verificar si hay sesión activa
                HttpSession session = request.getSession(false); // false = no crear nueva sesión

                if (session != null && session.getAttribute("usuarioLogueado") != null) {
                    // Caso 1: Hay sesión -> Redirige a crearUsuario.jsp
                    response.sendRedirect("JSP/crearUsuario.jsp");
                } else {
                    // Caso 2: No hay sesión -> Redirige al login con mensaje de éxito
                    request.setAttribute("exitoRegistro", "Usuario registrado con éxito");
                    request.getRequestDispatcher("JSP/login.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("JSP/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void loginUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String nameUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasenaUsuario");
            Usuario usuario = usuarioDAO.loginUsuario(nameUsuario,contrasena);

            if(usuario != null){
                //Crear una sesion o recuperar la existente
                HttpSession session = request.getSession();

                //Guardar el objeto usuario en la sesion
                session.setAttribute("usuarioLogueado", usuario);

                response.sendRedirect(request.getContextPath() + "/RegistroServlet?accion=listarRegistros");
            }else{
                // SI FALLA EL LOGIN, redirige al login.jsp PERO CON UN MENSAJE
                request.setAttribute("errorLogin", "Credenciales incorrectas. Inténtalo nuevamente.");
                request.getRequestDispatcher("JSP/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
}
