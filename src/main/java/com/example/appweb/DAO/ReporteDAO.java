package com.example.appweb.DAO;

import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.UTIL.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public List<RegistroPersona> obtenerRegistrosPorFecha(String desde, String hasta) throws Exception {
        List<RegistroPersona> lista = new ArrayList<>();
        String sql = "SELECT r.rut, p.nombre, r.fecha, r.tipo_registro, r.hora " +
                "FROM REGISTRO r JOIN PERSONA p ON r.rut = p.rut " +
                "WHERE TRUNC(r.fecha) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') ORDER BY r.fecha DESC ";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, desde);
            stmt.setString(2, hasta);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPersona reg = new RegistroPersona();
                reg.setRut(rs.getString("rut"));
                reg.setNombre(rs.getString("nombre"));
                reg.setFecha(rs.getDate("fecha"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));
                reg.setHora(rs.getString("hora"));
                lista.add(reg);
            }
        }
        return lista;
    }
    // Devuelve todos los ingresos y salidas del usuario (por rut) en el rango de fechas
    public List<RegistroPersona> obtenerRegistrosPorFechaUsuario(String rut, String desde, String hasta) throws Exception {
        List<RegistroPersona> lista = new ArrayList<>();
        String sql = "SELECT u.rut, u.nombreCompleto, r.fecha, r.tipo_registro, r.hora FROM REGISTRO r JOIN USUARIO u ON r.id_usuario = u.id_usuario" +
                " WHERE r.rut = ? AND TRUNC(r.fecha) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') ORDER BY r.fecha DESC";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rut);
            stmt.setString(2, desde);
            stmt.setString(3, hasta);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPersona reg = new RegistroPersona();
                reg.setRut(rs.getString("rut"));
                reg.setNombre(rs.getString("nombreCompleto"));
                reg.setFecha(rs.getDate("fecha"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));
                reg.setHora(rs.getString("hora"));
                lista.add(reg);
            }
        }
        return lista;
    }

    public List<RegistroPersona> obtenerRegistrosDependencias() throws Exception {
        List<RegistroPersona> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, r.rut, r.fecha, r.tipo_registro, r.hora FROM REGISTRO r " +
                "JOIN PERSONA p ON r.rut = p.rut " +
                "INNER JOIN (SELECT rut, MAX(TO_CHAR(fecha, 'YYYY-MM-DD') || ' ' || hora) AS ultima_fecha_hora " +
                "FROM REGISTRO GROUP BY rut) ult " +
                "ON (TO_CHAR(r.fecha, 'YYYY-MM-DD') || ' ' || r.hora) = ult.ultima_fecha_hora AND r.rut = ult.rut " +
                "WHERE r.tipo_registro = 'INGRESO' ORDER BY r.fecha DESC ";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPersona reg = new RegistroPersona();
                reg.setRut(rs.getString("rut"));
                reg.setNombre(rs.getString("nombre"));
                reg.setFecha(rs.getDate("fecha"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));
                reg.setHora(rs.getString("hora"));
                lista.add(reg);
            }
        }
        return lista;
    }

    public int contarTotalPersonas() throws Exception {
        String sql = "SELECT COUNT(*) FROM PERSONA";
        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int contarUsuariosSistema() throws Exception {
        String sql = "SELECT COUNT(*) FROM USUARIO";
        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int contarPresentesHoy() throws Exception {
        String sql = "SELECT COUNT(DISTINCT rut) FROM REGISTRO " +
                "WHERE tipo_registro = 'INGRESO' AND fecha = TRUNC(SYSDATE)";
        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    public int contarDependenciasActivas() throws Exception {
        String sql = "SELECT COUNT(DISTINCT r1.rut) " +
                "FROM REGISTRO r1 " +
                "WHERE r1.tipo_registro = 'INGRESO' " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM REGISTRO r2 " +
                "    WHERE r2.rut = r1.rut AND r2.tipo_registro = 'SALIDA' " +
                "    AND ((r2.fecha > r1.fecha) OR " +
                "         (r2.fecha = r1.fecha AND TO_TIMESTAMP(r2.hora, 'HH24:MI:SS') > TO_TIMESTAMP(r1.hora, 'HH24:MI:SS'))) " +
                ")";
        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
