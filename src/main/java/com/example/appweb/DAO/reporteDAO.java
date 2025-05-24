package com.example.appweb.DAO;

import com.example.appweb.MODELO.Registro;
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

        String sql = "SELECT r.rut, p.nombre, r.fecha, r.tipo_registro, r.hora " +
                "FROM registro r " +
                "JOIN persona p ON r.rut = p.rut " +
                "WHERE TRUNC(r.fecha) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

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
                reg.setFecha(rs.getDate("fecha"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));
                reg.setHora(rs.getString("hora"));

                lista.add(reg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<RegistroPersona> obtenerRegistrosDependencias() {
        List<RegistroPersona> listaDependencia = new ArrayList<>();
        String SQL = "SELECT r1.rut,p.nombre, r1.fecha,r1.tipo_registro , r1.hora FROM REGISTRO r1 JOIN PERSONA p ON r1.rut = p.rut WHERE r1.tipo_registro = 'INGRESO' AND NOT EXISTS " +
            "(SELECT 1 FROM REGISTRO r2 WHERE r2.rut = r1.rut AND r2.tipo_registro = 'SALIDA' AND r2.fecha = r1.fecha AND r2.hora > r1.hora)";

        try {
            Connection conn = ConexionDB.getInstance().getConexion();
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPersona reg = new RegistroPersona();
                reg.setRut(rs.getString("rut"));
                reg.setNombre(rs.getString("nombre"));
                reg.setFecha(rs.getDate("fecha"));
                reg.setTipoRegistro(rs.getString("tipo_registro"));
                reg.setHora(rs.getString("hora"));
                listaDependencia.add(reg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDependencia;
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

    public int dependenciasSistema() {
        int totalDependencias = 0;
        String sql = "SELECT COUNT(DISTINCT r1.rut) " +
                "FROM REGISTRO r1 " +
                "WHERE r1.tipo_registro = 'INGRESO' " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM REGISTRO r2 " +
                "    WHERE r2.rut = r1.rut " +
                "    AND r2.tipo_registro = 'SALIDA' " +
                "    AND ( " +
                "        (r2.fecha > r1.fecha) OR " +  // SALIDA en fecha posterior
                "        (r2.fecha = r1.fecha AND TO_TIMESTAMP(r2.hora, 'HH24:MI:SS') > TO_TIMESTAMP(r1.hora, 'HH24:MI:SS')) " +  // SALIDA mismo día pero hora posterior
                "    ) " +
                ")";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalDependencias = rs.getInt(1);
                System.out.println("[DEBUG] Personas en dependencias (histórico): " + totalDependencias);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalDependencias;
    }
    public double obtenerPorcentajeAsistenciaHoy() {
        double porcentaje = 0.0;
        String totalSQL = "SELECT COUNT(*) AS total FROM PERSONA";
        String ingresoSQL = "SELECT COUNT(DISTINCT rut) AS presentes FROM REGISTRO WHERE tipo_registro = 'INGRESO' AND fecha = TRUNC(SYSDATE)";
        //String dependenciasSQL = "SELECT COUNT(p.nombre) AS dependencia FROM REGISTRO r JOIN PERSONA p ON r.rut = p.rut WHERE r.tipo_registro = 'INGRESO' AND r.fecha = TRUNC(SYSDATE)";

        try (Connection conn = ConexionDB.getInstance().getConexion();
             PreparedStatement pstTotal = conn.prepareStatement(totalSQL);
             PreparedStatement pstIngreso = conn.prepareStatement(ingresoSQL);
             //PreparedStatement pstDependencias = conn.prepareStatement(dependenciasSQL);

             ResultSet rsTotal = pstTotal.executeQuery();
             //ResultSet rsDependencias = pstDependencias.executeQuery()
             ResultSet rsIngreso = pstIngreso.executeQuery()) {

            int total = 0;
            int presentes = 0;
            //int presenteDependencias = 0;

            // Leer el total de personas
            if (rsTotal.next()) {
                total = rsTotal.getInt("total");
                System.out.println("Total de personas registradas: " + total); // Sout aquí
            }

            // Leer los presentes hoy
            if (rsIngreso.next()) {
                presentes = rsIngreso.getInt("presentes");
                System.out.println("Personas que ingresaron hoy: " + presentes); // Sout aquí
            }
            //if (rsDependencias.next()) {
              //  presenteDependencias = rsDependencias.getInt("dependencia");
                //System.out.println("Personas que estan actualmente en las dependencias: " + presenteDependencias);
            //}

            // Calcular porcentaje
            if (total > 0) {
                porcentaje = (presentes * 100.0) / total;
                System.out.println("Porcentaje de asistencia hoy: " + porcentaje + "%"); // Sout del resultado
            } else {
                System.out.println("No hay personas registradas (total = 0)."); // Manejo de caso sin datos
            }

        } catch (Exception e) {
            System.err.println("Error al calcular el porcentaje de asistencia: " + e.getMessage()); // Error en rojo (err)
            e.printStackTrace();
        }
        return porcentaje;
    }
}
