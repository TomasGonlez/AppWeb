package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.DAO.registroDAO;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("listarRegistros".equals(accion)) {
            listarRegistros(request, response);
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
                response.sendRedirect("JSP/login2.jsp");
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
            String contrasena = request.getParameter("contrasenaUsuario");
            Usuario usuario = usuarioDAO.loginUsuario(nameUsuario,contrasena);

            if(usuario != null){
                //Crear una sesion o recuperar la existente
                HttpSession session = request.getSession();

                //Guardar el objeto usuario en la sesion
                session.setAttribute("usuarioLogueado", usuario);

                response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=listarRegistros");
            }else{
                // SI FALLA EL LOGIN, redirige al login2.jsp PERO CON UN MENSAJE
                request.setAttribute("errorLogin", "Credenciales incorrectas. Inténtalo nuevamente.");
                request.getRequestDispatcher("JSP/login2.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void listarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crea una instancia de RegistroDAO (asegúrate de haberla creado)
        registroDAO registroDAO = new registroDAO();

        // Obtiene la lista combinada de registros con nombre
        List<RegistroPersona> lista = registroDAO.obtenerRegistros();

        // Añade la lista al request
        request.setAttribute("listaRegistros", lista);

        // Redirige al JSP
        request.getRequestDispatcher("JSP/inicio.jsp").forward(request, response);
    }
}
