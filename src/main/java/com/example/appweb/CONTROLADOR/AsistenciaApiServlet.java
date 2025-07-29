package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.Asistencia;
import com.example.appweb.SERVICIO.AsistenciaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/asistencias/*")
public class AsistenciaApiServlet extends HttpServlet {
    private AsistenciaService asistenciaService;

    @Override
    public void init() throws ServletException {
        this.asistenciaService = (AsistenciaService) getServletContext().getAttribute("asistenciaService");
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) session.getAttribute("permisos");
        return permisos != null && permisos.contains("ver_asistencia_reciente");
    }

    private Integer getUserId(HttpSession session) {
        if (session == null) return null;
        Object usuarioObj = session.getAttribute("usuario");
        if (usuarioObj instanceof com.example.appweb.MODELO.Usuario usuario) {
            return usuario.getIdUsuario();
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        boolean admin = isAdmin(session);
        Integer userId = getUserId(session);
        System.out.println("User ID: " + userId + ", Admin: " + admin);
        List<Asistencia> asistencias;
        if (admin) {
            asistencias = asistenciaService.obtenerTodasAsistencias();
        } else if (userId != null) {
            asistencias = asistenciaService.obtenerAsistenciasPorUsuario(userId);
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"ok\", \"asistencias\":[");
        for (int i = 0; i < asistencias.size(); i++) {
            Asistencia a = asistencias.get(i);
            sb.append(a.toJson());
            if (i < asistencias.size() - 1) sb.append(",");
        }
        sb.append("]}");
        out.print(sb.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        Integer userId = getUserId(session);
        if (userId == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        String tipo = request.getParameter("tipo"); // entrada o salida
        String rut = request.getParameter("rut");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        if (tipo == null || fecha == null || hora == null || rut == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"Faltan campos obligatorios\"}");
            return;
        }
        boolean creado = asistenciaService.crearAsistencia(userId, rut, tipo, fecha, hora);
        if (creado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Registro creado\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No se pudo crear\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        String idStr = request.getPathInfo() != null ? request.getPathInfo().replace("/", "") : null;
        String tipo = request.getParameter("tipo");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        if (idStr == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"ID requerido\"}");
            return;
        }
        int id = Integer.parseInt(idStr);
        boolean actualizado = asistenciaService.actualizarAsistencia(id, tipo, fecha, hora);
        if (actualizado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Registro actualizado\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No se pudo actualizar\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        String idStr = request.getPathInfo() != null ? request.getPathInfo().replace("/", "") : null;
        if (idStr == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"ID requerido\"}");
            return;
        }
        int id = Integer.parseInt(idStr);
        boolean eliminado = asistenciaService.eliminarAsistencia(id);
        if (eliminado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Registro eliminado\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No se pudo eliminar\"}");
        }
    }
}
