package com.example.appweb.DAO;

import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public List<RegistroPersona> obtenerRegistros(){
        List<RegistroPersona> lista = new ArrayList<>();
        String sql="SELECT p.rut, p.nombre, r.tipo_registro, r.fecha_hora FROM PERSONA p JOIN REGISTRO r ON p.rut = r.rut";
        try(Connection conn = ConexionDB.getInstance().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                RegistroPersona rp = new RegistroPersona();
                rp.setRut(rs.getString("rut"));
                rp.setNombre(rs.getString("nombre"));
                rp.setTipoRegistro(rs.getString("tipo_registro"));
                rp.setFechaHora(rs.getDate("fecha_hora"));
                lista.add(rp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }
}
