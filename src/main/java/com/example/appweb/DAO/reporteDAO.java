package com.example.appweb.DAO;

import com.example.appweb.MODELO.Registro;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporteDAO {

    public List<Registro> obtenerRegistrosPorFecha(String desde, String hasta) {
        List<Registro> lista = new ArrayList<>();

        String sql = "SELECT * FROM REGISTRO WHERE TRUNC(fecha_hora) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

        try {
            Connection conn = ConexionDB.getInstance().getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Registro reg = new Registro();
                reg.setIdRegistro(rs.getInt("id_registro"));
                reg.setRut(rs.getString("rut"));
                reg.setIdUsuario(rs.getInt("id_usuario"));
                reg.setFechaHora(rs.getDate("fecha_hora"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));

                lista.add(reg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
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
