package com.example.appweb.DAO;

import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class personaDAO {

    public boolean registrar(Persona persona) {

        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO PERSONA (rut,nombre) VALUES (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, persona.getRut());
            stmt.setString(2, persona.getNombre());
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


