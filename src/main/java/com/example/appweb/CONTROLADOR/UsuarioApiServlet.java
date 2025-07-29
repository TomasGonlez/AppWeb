package com.example.appweb.CONTROLADOR;

import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/usuarios/*")
public class UsuarioApiServlet extends HttpServlet {
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        this.usuarioService = (UsuarioService) getServletContext().getAttribute("usuarioService");
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) session.getAttribute("permisos");
        // Considera admin si tiene el permiso 'registrar_usuario'
        return permisos != null && permisos.contains("registrar_asistencia");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"ok\", \"usuarios\":[");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            sb.append("{\"id\":" + u.getIdUsuario() + ",\"nombre\":\"" + u.getNombreUser() + "\",\"rut\":\"" + u.getRut() + "\"}");
            if (i < usuarios.size() - 1) sb.append(",");
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
        if (!isAdmin(session)) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autorizado\"}");
            return;
        }
        String rut = request.getParameter("rut");
        String nombreCompleto = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String numero = request.getParameter("telefono");
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        // Obtener fecha actual del sistema
        java.time.LocalDate fechaCreacion = java.time.LocalDate.now();
        // Validar campos requeridos
        if (rut == null || nombreCompleto == null || correo == null || numero == null || nombreUsuario == null || clave == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"Faltan campos obligatorios\"}");
            return;
        }
        boolean creado = usuarioService.crearUsuarioApi(nombreCompleto, correo, numero, nombreUsuario, clave, fechaCreacion.toString(), rut);
        if (creado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Usuario creado\", "
                    + "\"correo\":\"" + correo + "\", "
                    + "\"numero\":\"" + numero + "\", "
                    + "\"nombreUsuario\":\"" + nombreUsuario + "\", "
                    + "\"fechaCreacion\":\"" + fechaCreacion + "\", "
                    + "\"rut\":\"" + rut + "\"}");
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
        // Debes parsear el id desde la URL y los datos desde el body (puedes usar un helper para leer JSON si lo deseas)
        // Aquí solo es un ejemplo básico
        String idStr = request.getPathInfo() != null ? request.getPathInfo().replace("/", "") : null;
        String nombre = request.getParameter("nombre");
        String clave = request.getParameter("clave");
        String rut = request.getParameter("rut");
        if (idStr == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"ID requerido\"}");
            return;
        }
        int id = Integer.parseInt(idStr);
        boolean actualizado = usuarioService.actualizarUsuarioApi(id, nombre, clave, rut);
        if (actualizado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Usuario actualizado\"}");
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
        boolean eliminado = usuarioService.eliminarUsuarioApi(id);
        if (eliminado) {
            out.print("{\"status\":\"ok\", \"mensaje\":\"Usuario eliminado\"}");
        } else {
            out.print("{\"status\":\"error\", \"mensaje\":\"No se pudo eliminar\"}");
        }
    }
}
