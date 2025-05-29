package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioDAO.existeNombreUsuario(nombreUsuario);
    }

    public boolean registrarUsuario(Usuario usuario) {
        return usuarioDAO.guardar(usuario);
    }

    public Usuario loginUsuario(String nombreUsuario, String contrasena) {
        return usuarioDAO.loginUsuario(nombreUsuario, contrasena);
    }


    public Usuario construirUsuarioDesdeRequest(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombreCompletoUser(request.getParameter("nombreCompletoUser"));
        usuario.setCorreoUser(request.getParameter("correoUser"));
        usuario.setNumeroUser(Integer.parseInt(request.getParameter("numeroUser")));
        usuario.setNombreUser(request.getParameter("nombreUser"));
        usuario.setContrasena(request.getParameter("contrasena"));
        usuario.setFechaCreacion(LocalDate.now());
        return usuario;
    }
}
