package com.example.appweb.DAO;

import com.example.appweb.MODELO.Persona;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PersonaDAO {

    public void registrar(Persona persona) {
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO PERSONA (rut,nombre) VALUES (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, persona.getRut());
            stmt.setString(2, persona.getNombre());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean buscarRut(String rut) {
        boolean existe = false;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT 1 FROM USUARIO WHERE rut = ?";
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
    public boolean buscarNombre(String nombre, String rut) {
        boolean existe = false;
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT nombreCompleto FROM USUARIO WHERE rut = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,rut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombreDB = rs.getString("nombreCompleto");
                if(nombre.equals(nombreDB)) {
                    existe= true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }
}


