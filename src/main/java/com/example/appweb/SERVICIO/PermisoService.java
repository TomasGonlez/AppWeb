package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.PermisoDAO;

import java.util.List;

public class PermisoService {

    private final PermisoDAO permisoDAO;

    public PermisoService(PermisoDAO permisoDAO) {
        this.permisoDAO = permisoDAO;
    }

    public List<String> ObtenerPermisosPorUsuario(int idUsuario){
        return permisoDAO.obtenerPermisos(idUsuario);
    }
}
