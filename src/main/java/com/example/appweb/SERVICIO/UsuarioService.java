package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class UsuarioService {
    // CRUD para API
    public java.util.List<Usuario> obtenerTodosUsuarios() {
        return usuarioDAO.listar();
    }

    public boolean crearUsuarioApi(String nombreC, String email, String numero, String nombreU, String clave, String fecha, String rut) {
        if (existeNombreUsuario(nombreU)) return false;
        Usuario u = new Usuario();
        u.setNombreCompletoUser(nombreC);
        u.setCorreoUser(email);
        u.setNumeroUser(Integer.parseInt(numero));
        u.setNombreUser(nombreU);
        u.setContrasena(clave);
        u.setFechaCreacion(LocalDate.parse(fecha));
        u.setRut(rut);
        //setear los campos nombrecompleto, correo, numero, nombreusuario, , fecha creacion y rut
        u.setFechaCreacion(java.time.LocalDate.now());
        // Puedes setear otros campos por defecto o null
        return usuarioDAO.guardar(u);
    }

    public boolean actualizarUsuarioApi(int id, String nombre, String clave, String rut) {
        // Buscar usuario existente
        java.util.List<Usuario> lista = usuarioDAO.listar();
        Usuario u = null;
        for (Usuario user : lista) {
            if (user.getIdUsuario() == id) {
                u = user;
                break;
            }
        }
        if (u == null) return false;
        if (nombre != null) u.setNombreUser(nombre);
        if (clave != null) u.setContrasena(clave);
        if (rut != null) u.setRut(rut);
        // Aquí deberías tener un método usuarioDAO.actualizar(u), lo simulo con eliminar+guardar
        // (Reemplaza esto por un update real si tienes el método)
        // Eliminar y volver a guardar NO es lo ideal, pero es un fallback si no tienes update
        // return usuarioDAO.actualizar(u);
        return false;
    }

    public boolean eliminarUsuarioApi(int id) {
        // Aquí deberías tener un método usuarioDAO.eliminar(id)
        // return usuarioDAO.eliminar(id);
        return false;
    }
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
