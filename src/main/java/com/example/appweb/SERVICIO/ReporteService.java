package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.ReporteDAO;
import com.example.appweb.MODELO.RegistroPersona;

import java.util.List;

public class ReporteService {
    private final ReporteDAO dao;

    public ReporteService() {
        this.dao = new ReporteDAO();
    }
    // Obtiene lista de registros por fecha (simple delegación)
    public List<RegistroPersona> obtenerPorFechas(String desde, String hasta) throws Exception {
        return dao.obtenerRegistrosPorFecha(desde, hasta);
    }

    // Obtiene lista de personas que aún están dentro
    public List<RegistroPersona> obtenerPorDependencias() throws Exception {
        return dao.obtenerRegistrosDependencias();
    }

    // Aplica lógica de negocio: calcula % de asistencia
    public double calcularPorcentajeAsistenciaHoy() throws Exception {
        int total = dao.contarTotalPersonas();
        int presentes = dao.contarPresentesHoy();
        if (total == 0) return 0.0;
        double porcentaje = (presentes * 100.0) / total;
        return Math.round(porcentaje*100.0)/100.0;
    }

    // Solo lectura directa (aún útil centralizar)
    public int contarPersonasSistema() throws Exception {
        return dao.contarTotalPersonas();
    }

    public int contarUsuariosSistema() throws Exception {
        return dao.contarUsuariosSistema();
    }

    public int contarDependenciasActivas() throws Exception {
        return dao.contarDependenciasActivas();
    }
}
