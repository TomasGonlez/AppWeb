
package com.example.appweb.SERVICIO;

import com.example.appweb.MODELO.Asistencia;
import com.example.appweb.DAO.AsistenciaDAO;

import java.util.List;

public class AsistenciaService {
    private final AsistenciaDAO asistenciaDAO;

    public AsistenciaService(AsistenciaDAO asistenciaDAO) {
        this.asistenciaDAO = asistenciaDAO;
    }

    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaDAO.obtenerTodas();
    }

    public List<Asistencia> obtenerAsistenciasPorUsuario(int idUsuario) {
        return asistenciaDAO.obtenerPorUsuario(idUsuario);
    }

    public boolean crearAsistencia(int idUsuario, String rut, String tipo, String fecha, String hora) {
        return asistenciaDAO.crear(idUsuario, rut, tipo, fecha, hora);
    }

    public boolean actualizarAsistencia(int id, String tipo, String fecha, String hora) {
        return asistenciaDAO.actualizar(id, tipo, fecha, hora);
    }

    public boolean eliminarAsistencia(int id) {
        return asistenciaDAO.eliminar(id);
    }
}
