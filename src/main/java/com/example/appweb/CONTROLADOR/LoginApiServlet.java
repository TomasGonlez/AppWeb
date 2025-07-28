package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/login")
public class LoginApiServlet extends HttpServlet {
    private UsuarioService usuarioService;

    public void init() throws ServletException {
        this.usuarioService = (UsuarioService) getServletContext().getAttribute("usuarioService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        Usuario usuario = usuarioService.loginUsuario(nombreUsuario, clave);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (usuario != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            // Obtener el rol desde la base de datos
            com.example.appweb.DAO.UsuarioDAO usuarioDAO = new com.example.appweb.DAO.UsuarioDAO();
            String rol = usuarioDAO.obtenerRolPorIdUsuario(usuario.getIdUsuario());
            session.setAttribute("rol", rol);
            // Obtener y guardar los permisos en la sesión
            com.example.appweb.SERVICIO.PermisoService permisoService = (com.example.appweb.SERVICIO.PermisoService) getServletContext().getAttribute("permisoService");
            if (permisoService != null) {
                java.util.List<String> permisos = permisoService.ObtenerPermisosPorUsuario(usuario.getIdUsuario());
                session.setAttribute("permisos", permisos);
            }
            out.print("{\"status\":\"ok\", "
                    + "\"id_usuario\":" + usuario.getIdUsuario() + ", "
                    + "\"nombre\":\"" + usuario.getNombreUser() + "\", "
                    + "\"rol\":\"" + (rol != null ? rol : "") + "\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"Credenciales inválidas\"}");
        }
        out.flush();
    }
}
