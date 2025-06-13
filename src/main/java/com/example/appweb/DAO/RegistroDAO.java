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

public class RegistroDAO {
    public boolean registrar(Registro registro) {
        try {
            // Validación solo para SALIDAS
            if ("SALIDA".equals(registro.getTipoRegistro())) {
                java.sql.Date ultimoIngreso = obtenerUltimaFechaIngreso(registro.getRut());
                String ultimaHora = obtenerUltimaHoraIngreso(registro.getRut());

                if (ultimoIngreso == null) {
                    throw new IllegalArgumentException("No hay un INGRESO registrado para este RUT.");
                }

                if (registro.getFecha().before(ultimoIngreso)) {
                    throw new IllegalArgumentException("La SALIDA no puede ser antes del último INGRESO (" + ultimoIngreso + ")");
                }
                if (registro.getFecha().equals(ultimoIngreso)) {
                    if (registro.getHora().compareTo(ultimaHora) <= 0) {
                        throw new IllegalArgumentException("La SALIDA debe ser posterior al último INGRESO en la misma fecha.");
                    }
                }
            }

            // Insertar registro si pasa las validaciones
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "INSERT INTO REGISTRO (rut, id_usuario, fecha, tipo_registro, hora) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, registro.getRut());
            stmt.setInt(2, registro.getIdUsuario());
            stmt.setDate(3, registro.getFecha());
            stmt.setString(4, registro.getTipoRegistro());
            stmt.setString(5, registro.getHora());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar: " + e.getMessage());
        }
    }
    public List<RegistroPersona> obtenerRegistros(){
        List<RegistroPersona> lista = new ArrayList<>();
        String sql="SELECT p.rut, p.nombre, r.tipo_registro, r.fecha, r.hora FROM PERSONA p JOIN REGISTRO r ON p.rut = r.rut ORDER BY id_registro DESC";
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
    public static String obtenerUltimoTipoRegistroGeneral(String rut) {
        String tipo = null;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT tipo_registro FROM REGISTRO WHERE rut = ? ORDER BY fecha DESC, hora DESC FETCH FIRST 1 ROWS ONLY";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipo = rs.getString("tipo_registro");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipo;
    }
    public boolean ExistenciaRegistro(String rut) {
        boolean existe = false;
        try {
            Connection con = ConexionDB.getInstance().getConexion();
            String sql = "SELECT 1 FROM REGISTRO WHERE rut = ? FETCH FIRST 1 ROWS ONLY";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }

    public java.sql.Date obtenerUltimaFechaIngreso(String rut) {
        String sql = "SELECT fecha FROM REGISTRO WHERE rut = ? AND tipo_registro = 'INGRESO' ORDER BY fecha DESC, hora DESC FETCH FIRST 1 ROWS ONLY";
        try (Connection con = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDate("fecha") : null; // Cambiado a getDate()
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String obtenerUltimaHoraIngreso(String rut) {
        String sql = "SELECT hora FROM REGISTRO WHERE rut = ? AND tipo_registro = 'INGRESO' ORDER BY id_Registro DESC FETCH FIRST 1 ROWS ONLY";
        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rut);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("hora");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
