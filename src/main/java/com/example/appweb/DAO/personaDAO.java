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
    public boolean buscarRut(String rut) {
        boolean existe = false;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT * FROM PERSONA WHERE rut = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                 existe= true;
            }
            return existe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }
}


