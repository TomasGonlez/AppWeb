package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.MODELO.RegistroPersona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.RegistroService;
import com.example.appweb.UTIL.RegistroUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegistroServlet extends HttpServlet {
    private RegistroService registroService;
    @Override
    public void init() throws ServletException{
        PersonaDAO personaDAO= new PersonaDAO();
        RegistroDAO registroDAO= new RegistroDAO();
        registroService = new RegistroService(personaDAO, registroDAO);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            HttpSession session = request.getSession(false);
            Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;
            if (usuario == null) {
                response.sendRedirect("JSP/login.jsp");
                return;
            }
            registroService.procesarRegistro(request, response, usuario);
        }else {
            response.sendRedirect("JSP/error.jsp");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("listarRegistros".equals(accion)) {
            try {
                listarRegistros(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect("JSP/error.jsp");
        }
    }
    private void listarRegistros(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<RegistroPersona> registros = RegistroUtils.obtenerTodosLosRegistros();
        Map<String, Object> metricas = RegistroUtils.obtenerMetricasDelSistema();
        String fechaFormateada = RegistroUtils.obtenerFechaActualFormateada();

        RegistroUtils.configurarAtributosVista(request, registros, metricas, fechaFormateada);

        RegistroUtils.redirigirAVista(request, response, "JSP/ver_registros.jsp");
    }
}
