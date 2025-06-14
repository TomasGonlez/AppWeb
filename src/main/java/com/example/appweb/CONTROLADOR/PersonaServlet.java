package com.example.appweb.CONTROLADOR;


import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.PersonaService;
import com.example.appweb.UTIL.Errores;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class PersonaServlet extends HttpServlet {
    private PersonaService personaService;

    // Constantes para parámetros y rutas
    private static final String PARAM_ACCION = "accion";
    private static final String PARAM_RUT_PERSONA = "rutPersona";
    private static final String PARAM_NOMBRE_PERSONA = "nombrePersona";

    private static final String ACCION_REGISTRAR = "registrar";
    private static final String VISTA_LOGIN = "JSP/login.jsp";
    private static final String VISTA_ERROR = "JSP/error.jsp";
    private static final String VISTA_REGISTRO_EXITOSO = "JSP/registrar_empleado.jsp";

    private static final String ATTR_USUARIO_LOGUEADO = "usuarioLogueado";
    private static final String ATTR_EXITO_REGISTRO = "exitoRegistro";

    public void init(){
        this.personaService = (PersonaService) getServletContext().getAttribute("personaService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute(ATTR_USUARIO_LOGUEADO) : null;
        if (usuario == null) {
            response.sendRedirect(VISTA_LOGIN);
            return;
        }

        String accion = request.getParameter(PARAM_ACCION);
        if (ACCION_REGISTRAR.equals(accion)) {
            registrarPersona(request, response);
        } else {
            response.sendRedirect(VISTA_ERROR);
        }
    }

    private void registrarPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rut = request.getParameter(PARAM_RUT_PERSONA);
        String nombre = request.getParameter(PARAM_NOMBRE_PERSONA);

        if (personaService.validarRut(rut)) {
            Errores.enviarErrorRegistrarPersona(request, response, "El rut ingresado ya existe");
            return;
        }

        Persona persona = personaService.crearPersona(rut, nombre);
        personaService.registrarPersona(persona);

        request.setAttribute(ATTR_EXITO_REGISTRO, "Empleado registrado correctamente");
        request.getRequestDispatcher(VISTA_REGISTRO_EXITOSO).forward(request, response);
    }
}
