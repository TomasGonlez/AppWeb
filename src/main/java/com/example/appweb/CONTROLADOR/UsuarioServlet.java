package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

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
        } else if ("buscar".equals(accion)) {
            buscarUsuarioPorId(request, response);
        } else if ("login".equals(accion)) {
            loginUsuario(request, response);
        } else {
            response.sendRedirect("JSP/error.jsp");
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombreCompleto = request.getParameter("nombreCompletoUser");
        String correo = request.getParameter("correoUser");
        int numero = Integer.parseInt(request.getParameter("numeroUser"));
        String nombre = request.getParameter("nombreUser");
        String contrasena = request.getParameter("contrasena");
        String fechaStr = request.getParameter("fechaCreacion");

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreCompletoUser(nombreCompleto);
            nuevoUsuario.setCorreoUser(correo);
            nuevoUsuario.setNumeroUser(numero);
            nuevoUsuario.setNombreUser(nombre);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setFechaCreacion(fecha);

            boolean exito = usuarioDAO.guardar(nuevoUsuario);

            if (exito) {
                response.sendRedirect("JSP/exito.jsp");
            } else {
                response.sendRedirect("JSP/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void buscarUsuarioPorId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario != null) {
                request.setAttribute("usuarioEncontrado", usuario);
                request.getRequestDispatcher("JSP/usuarioEncontrado.jsp").forward(request, response);
            } else {
                response.sendRedirect("JSP/noEncontrado.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }

    private void loginUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String nameUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");
            Usuario usuario = usuarioDAO.loginUsuario(nameUsuario,contrasena);

            if(usuario != null){
                request.setAttribute("usuarioEncontrado2", usuario);
                request.getRequestDispatcher("JSP/usuarioEncontrado2.jsp").forward(request, response);
            }else{
                response.sendRedirect("JSP/error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
}
