package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.RolDAO;

public class RolService {
    private final RolDAO rolDAO;

    public RolService(RolDAO rolDAO) {
        this.rolDAO = rolDAO;
    }

    public int obtenerIDRolUsuario(String rol){
        return rolDAO.idRolUsuario(rol);
    }
    public boolean registrarRolUsuario(int id_usuario, int id_rol){
        return rolDAO.insertarROL(id_usuario, id_rol);
    }
}
