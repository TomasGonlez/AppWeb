package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/login")
public class LoginApiServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.loginUsuario(nombreUsuario, clave);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (usuario != null) {
            out.print("{\"status\":\"ok\", \"id_usuario\":" + usuario.getIdUsuario() + ", \"nombre\":\"" + usuario.getNombreUser() + "\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"Credenciales inválidas\"}");
        }
        out.flush();
    }
}
