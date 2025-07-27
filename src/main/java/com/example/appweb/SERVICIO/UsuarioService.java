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

    public boolean registrarUsuario(Usuario U) {
        //Validación de negocio: el nombre de usuario debe ser único
        if(existeNombreUsuario(U.getNombreUser())){
            return false;
        }
        return usuarioDAO.guardar(U);
    }

    public Usuario loginUsuario(String nombreUsuario, String contrasena) {
        return usuarioDAO.loginUsuario(nombreUsuario, contrasena);
    }

    public Usuario construirUsuarioDesdeRequest(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setRut(request.getParameter("rut"));
        usuario.setNombreCompletoUser(request.getParameter("nombreCompletoUser"));
        usuario.setCorreoUser(request.getParameter("correoUser"));
        usuario.setNumeroUser(Integer.parseInt(request.getParameter("numeroUser")));
        usuario.setNombreUser(request.getParameter("nombreUser"));
        usuario.setContrasena(request.getParameter("contrasena"));
        usuario.setFechaCreacion(LocalDate.now());
        return usuario;
    }
    public int obtenerIDUsuario(String NombreUsuario){
        return usuarioDAO.idUsuario(NombreUsuario);
    }
}
