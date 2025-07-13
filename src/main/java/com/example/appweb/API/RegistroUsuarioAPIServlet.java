package com.example.appweb.API;

import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/registroUser")
public class RegistroUsuarioAPIServlet extends HttpServlet {

    private UsuarioService usuarioService;

    public void init() throws ServletException {
        this.usuarioService = (UsuarioService) getServletContext().getAttribute("usuarioService");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario nuevoUsuario = usuarioService.construirUsuario(request);
        boolean exito = usuarioService.registrarUsuario(nuevoUsuario);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (exito) {
            out.print("{\"status\":\"ok\", \"id_usuario\":" + nuevoUsuario.getIdUsuario() + ", \"nombreUsuario\":\"" + nuevoUsuario.getNombreUser() + "\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No se logro crear correctamente el usuario\"}");
        }
        out.flush();
    }
}
