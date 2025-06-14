package com.example.appweb.CONTROLADOR;

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

    // Constantes de parámetros
    private static final String PARAM_ACCION = "accion";

    // Acciones
    private static final String ACCION_INGRESAR = "ingresar";
    private static final String ACCION_LISTAR_REGISTROS = "listarRegistros";

    // Rutas JSP
    private static final String VISTA_LOGIN = "JSP/login.jsp";
    private static final String VISTA_ERROR = "JSP/error.jsp";
    private static final String VISTA_VER_REGISTROS = "JSP/ver_registros.jsp";

    // Atributos sesión
    private static final String ATTR_USUARIO_LOGUEADO = "usuarioLogueado";

    @Override
    public void init() throws ServletException {
        this.registroService = (RegistroService) getServletContext().getAttribute("registroService");    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter(PARAM_ACCION);

        if (ACCION_INGRESAR.equals(accion)) {
            HttpSession session = request.getSession(false);
            Usuario usuario = (session != null) ? (Usuario) session.getAttribute(ATTR_USUARIO_LOGUEADO) : null;
            if (usuario == null) {
                response.sendRedirect(VISTA_LOGIN);
                return;
            }
            registroService.procesarRegistro(request, response, usuario);
        } else {
            response.sendRedirect(VISTA_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter(PARAM_ACCION);

        if (ACCION_LISTAR_REGISTROS.equals(accion)) {
            try {
                listarRegistros(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(VISTA_ERROR);
        }
    }

    private void listarRegistros(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<RegistroPersona> registros = RegistroUtils.obtenerTodosLosRegistros();
        Map<String, Object> metricas = RegistroUtils.obtenerMetricasDelSistema();
        String fechaFormateada = RegistroUtils.obtenerFechaActualFormateada();

        RegistroUtils.configurarAtributosVista(request, registros, metricas, fechaFormateada);
        RegistroUtils.redirigirAVista(request, response, VISTA_VER_REGISTROS);
    }
}