package com.example.appweb.DAO;

import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listar() {
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT * FROM USUARIO";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Usuario user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setRut(rs.getString("rut"));
                user.setNombreUser(rs.getString("nombreUser"));
                user.setContrasena(rs.getString("contrasena"));
                user.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                listaUsuario.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuario;
    }

    public boolean guardar(Usuario user) {
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO USUARIO (rut, nombreCompleto, correo, numero, nombreUser, contrasena, fecha_creacion) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getRut());
            stmt.setString(2, user.getNombreCompletoUser());
            stmt.setString(3, user.getCorreoUser());
            stmt.setInt(4, user.getNumeroUser());
            stmt.setString(5, user.getNombreUser());
            stmt.setString(6, user.getContrasena());
            stmt.setDate(7, java.sql.Date.valueOf(user.getFechaCreacion()));
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Usuario loginUsuario(String nombreusuario, String contrasena) {
        Usuario user = null;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT * FROM USUARIO WHERE nombreUser = ? AND contrasena = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombreusuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setRut(rs.getString("rut"));
                user.setNombreCompletoUser(rs.getString("nombreCompleto"));
                user.setCorreoUser(rs.getString("correo"));
                user.setNumeroUser(rs.getInt("numero"));
                user.setNombreUser(rs.getString("nombreUser"));
                user.setContrasena(rs.getString("contrasena"));
                user.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                // El rol se obtiene por consulta aparte si se requiere
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public boolean existeNombreUsuario(String nombreUsuario) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE nombreUser = ?";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }
    public int idUsuario(String nombreUsuario) {
        int idUser = 0;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT id_usuario FROM USUARIO WHERE nombreUser = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idUser = rs.getInt("id_usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idUser;
    }

    // Método para obtener el rol de un usuario por su id
    public String obtenerRolPorIdUsuario(int idUsuario) {
        String rol = null;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT r.nombre_rol FROM USUARIO_ROL ur " +
                         "JOIN ROL r ON ur.id_rol = r.id_rol " +
                         "WHERE ur.id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rol = rs.getString("nombre_rol");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rol;
    }
}


