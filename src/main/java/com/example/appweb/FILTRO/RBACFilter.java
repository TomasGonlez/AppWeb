package com.example.appweb.FILTRO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;

@WebFilter("/protegido/*") // Cambia este patrón según las rutas a proteger
public class RBACFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            int idUsuario = usuario.getIdUsuario();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String userRole = usuarioDAO.obtenerRolPorIdUsuario(idUsuario);
            String uri = req.getRequestURI();
            // ADMIN puede acceder a todo
            if ("ADMIN".equals(userRole)) {
                chain.doFilter(request, response);
                return;
            }
            // USUARIO solo puede acceder a registrar asistencia
            if ("USUARIO".equals(userRole)) {
                // Ajusta la ruta según tu estructura real
                if (uri.contains("registrar_entrada_salida") || uri.contains("cerrarSesion")) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        res.sendRedirect(req.getContextPath() + "/error.jsp");
    }
}
