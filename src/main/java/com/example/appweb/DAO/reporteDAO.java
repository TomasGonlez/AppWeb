package com.example.appweb.DAO;

import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporteDAO {

    public List<RegistroPersona> obtenerRegistrosPorFecha(String desde, String hasta) {
        List<RegistroPersona> lista = new ArrayList<>();

        String sql = "SELECT r.rut, p.nombre, r.fecha_hora, r.tipo_registro " +
                "FROM registro r " +
                "JOIN persona p ON r.rut = p.rut " +
                "WHERE TRUNC(r.fecha_hora) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

        try {
            Connection conn = ConexionDB.getInstance().getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPersona reg = new RegistroPersona();
                reg.setRut(rs.getString("rut"));
                reg.setNombre(rs.getString("nombre"));
                reg.setFechaHora(rs.getDate("fecha_hora"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));

                lista.add(reg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    public int personasSistema(){
        int totalPersonas = 0;
        String sql = "SELECT COUNT(*) FROM PERSONA";

        try{
            Connection conn = ConexionDB.getInstance().getConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalPersonas = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error al consultar en la database: "+e.getMessage());
        }
        return totalPersonas;
    }
    public int usuariosSistema(){
        int totalUsuario = 0;
        try {
            Connection conn = ConexionDB.getInstance().getConexion();
            String sql = "SELECT COUNT(*) AS total FROM USUARIO";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalUsuario = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al consultar en la database: "+e.getMessage());
        }
        return totalUsuario;
    }
    public double obtenerPorcentajeAsistenciaHoy() {
        double porcentaje = 0.0;
        String totalSQL = "SELECT COUNT(*) AS total FROM PERSONA";
        String ingresoSQL = "SELECT COUNT(DISTINCT r.rut) AS presentes FROM REGISTRO r WHERE r.tipo_registro = 'INGRESO' AND DATE(r.fecha_hora) = CURRENT_DATE";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement pstTotal = conn.prepareStatement(totalSQL);
             PreparedStatement pstIngreso = conn.prepareStatement(ingresoSQL)) {

            ResultSet rsTotal = pstTotal.executeQuery();
            int totalper = 0;
            totalper = rsTotal.getInt("totalper");
            System.out.println("Las personas que hay en la tabla PERSONA: " + totalper);

            ResultSet rsIngreso = pstIngreso.executeQuery();
            int peringreso = 0;
            peringreso = rsIngreso.getInt("peringreso");
            System.out.println("Las personas que han registrado su INGRESO hoy son: " + peringreso);

            if (rsTotal.next() && rsIngreso.next()) {
                int total = rsTotal.getInt("total");
                int presentes = rsIngreso.getInt("presentes");

                if (total > 0) {
                    porcentaje = (presentes * 100.0) / total;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return porcentaje;
    }
}
