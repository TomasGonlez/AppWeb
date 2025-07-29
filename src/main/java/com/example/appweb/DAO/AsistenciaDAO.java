package com.example.appweb.DAO;

import com.example.appweb.MODELO.Asistencia;
import com.example.appweb.UTIL.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {
    public List<Asistencia> obtenerTodas() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM REGISTRO ORDER BY fecha DESC, hora DESC";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Asistencia> obtenerPorUsuario(int idUsuario) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM REGISTRO WHERE id_usuario = ? ORDER BY fecha DESC, hora DESC";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean crear(int idUsuario, String rut, String tipo, String fecha, String hora) {
        String sql = "INSERT INTO REGISTRO (rut,id_usuario,fecha,tipo_registro,hora) VALUES (?, ?, TO_DATE('YYYY-MM-DD', ?), ?)";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, rut);
            stmt.setInt(2, idUsuario);
            stmt.setString(3, fecha);
            stmt.setString(4, tipo);
            stmt.setString(5, hora);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(int id, String tipo, String fecha, String hora) {
        String sql = "UPDATE REGISTRO SET tipo_registro=?, fecha=TO_DATE(?, 'YYYY-MM-DD'), hora=? WHERE id_registro=?";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setString(2, fecha);
            stmt.setString(3, hora);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM REGISTRO WHERE id_registro=?";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    private Asistencia mapRow(ResultSet rs) throws SQLException {
        return new Asistencia(
                rs.getInt("id_registro"),
                rs.getInt("id_usuario"),
                rs.getString("tipo_registro"),
                rs.getDate("fecha").toString(),
                rs.getString("hora")
        );
    }
}
