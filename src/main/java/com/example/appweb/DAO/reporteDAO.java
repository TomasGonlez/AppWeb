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
}
