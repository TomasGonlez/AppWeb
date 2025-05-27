package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.ReporteDAO;
import com.example.appweb.MODELO.RegistroPersona;

import java.util.List;

public class ReporteService {
    private final ReporteDAO dao;

    public ReporteService() {
        this.dao = new ReporteDAO();
    }
    public List<RegistroPersona> obtenerPorFechas(String desde, String hasta) throws Exception {
        return dao.obtenerRegistrosPorFecha(desde, hasta);
    }

    public List<RegistroPersona> obtenerPorDependencias() throws Exception {
        return dao.obtenerRegistrosDependencias();
    }
}
