package com.example.appweb.DAO;

import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class registroDAO {
    public boolean registrar(Registro registro) {
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO REGISTRO (rut,id_usuario,fecha_hora,tipo_registro) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, registro.getRut());
            stmt.setInt(2, registro.getIdUsuario());
            stmt.setDate(3, registro.getFechaHora());
            stmt.setString(4,registro.getTipoRegistro());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
