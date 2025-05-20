package com.example.appweb.DAO;

import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class registroDAO {
    public boolean registrar(Registro registro) {
        try{
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO REGISTRO (rut,id_usuario,fecha,tipo_registro,hora) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, registro.getRut());
            stmt.setInt(2, registro.getIdUsuario());
            stmt.setDate(3, registro.getFecha());
            stmt.setString(4,registro.getTipoRegistro());
            stmt.setString(5,registro.getHora());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<RegistroPersona> obtenerRegistros(){
        List<RegistroPersona> lista = new ArrayList<>();
        String sql="SELECT p.rut, p.nombre, r.tipo_registro, r.fecha, r.hora FROM PERSONA p JOIN REGISTRO r ON p.rut = r.rut";
        try(Connection conn = ConexionDB.getInstance().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                RegistroPersona rp = new RegistroPersona();
                rp.setRut(rs.getString("rut"));
                rp.setNombre(rs.getString("nombre"));
                rp.setTipoRegistro(rs.getString("tipo_registro"));
                rp.setFecha(rs.getDate("fecha"));
                rp.setHora(rs.getString("hora"));
                lista.add(rp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }
    public static String obtenerUltimoTipoRegistro(String rut, Date fecha) {
        String tipo = null;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT tipo_registro FROM REGISTRO WHERE rut = ? AND fecha = ? ORDER BY hora DESC FETCH FIRST 1 ROWS ONLY";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rut);
            stmt.setDate(2, (java.sql.Date) fecha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipo = rs.getString("tipo_registro");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipo;
    }
}
