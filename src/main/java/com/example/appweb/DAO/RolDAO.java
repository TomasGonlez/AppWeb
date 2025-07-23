package com.example.appweb.DAO;

import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RolDAO {

    public boolean insertarROL(int idUser,  int idRol){
        try {
            String sql = "INSERT INTO USUARIO_ROL (id_usuario, id_rol) VALUES (?,?)";
            Connection con = ConexionDB.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUser);
            ps.setInt(2, idRol);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int idRolUsuario(String rol) {
        int ID = 0;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT id_rol FROM ROL WHERE nombre_rol = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ID = rs.getInt("id_rol");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }

}
