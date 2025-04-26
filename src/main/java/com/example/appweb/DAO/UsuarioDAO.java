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
            String sql = "INSERT INTO USUARIO (nombreCompleto, correo, numero,nombreUser, contrasena, fecha_creacion) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getNombreCompletoUser());
            stmt.setString(2, user.getCorreoUser());
            stmt.setInt(3, user.getNumeroUser());
            stmt.setString(4, user.getNombreUser());
            stmt.setString(5, user.getContrasena());
            stmt.setDate(6, java.sql.Date.valueOf(user.getFechaCreacion()));
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Usuario buscarPorId(int id) {
        Usuario user = null;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT * FROM USUARIO WHERE id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setNombreUser(rs.getString("nombreUser"));
                user.setContrasena(rs.getString("contrasena"));
                user.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
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
                user.setNombreCompletoUser(rs.getString("nombreCompleto"));
                user.setCorreoUser(rs.getString("correo"));
                user.setNumeroUser(rs.getInt("numero"));
                user.setNombreUser(rs.getString("nombreUser"));
                user.setContrasena(rs.getString("contrasena"));
                user.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}


