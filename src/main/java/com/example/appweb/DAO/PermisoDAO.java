package com.example.appweb.DAO;

import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PermisoDAO {

    public List<String>obtenerPermisos(int id){
        List<String> permisos=new ArrayList<>();
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT p.nomber_permiso FROM USUARIO u JOIN USUARIO_ROL ur ON u.id_usuario = ur.id_usuario " +
                    "JOIN ROL_PERMISO rp ON ur.id_rol = rp.id_rol JOIN PERMISO p ON rp.id_permiso = p.id_permiso WHERE u.id_usuario = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                permisos.add(rs.getString("nomber_permiso"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permisos;
    }
}
