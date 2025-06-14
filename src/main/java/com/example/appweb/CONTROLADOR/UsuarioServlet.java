package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.SERVICIO.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UsuarioServlet extends HttpServlet {

    // Parámetros del request
    private static final String PARAM_ACCION = "accion";
    private static final String PARAM_ORIGEN = "origen";
    private static final String PARAM_NOMBRE_USUARIO = "nombreUsuario";
    private static final String PARAM_CONTRASENA_USUARIO = "contrasenaUsuario";

    // Atributos del request
    private static final String ATTR_ERROR_REGISTRO = "errorRegistroUsuario";
    private static final String ATTR_ERROR_LOGIN = "errorLogin";
    private static final String ATTR_EXITO_REGISTRO = "exitoRegistro";
    private static final String ATTR_USUARIO_LOGUEADO = "usuarioLogueado";

    // Valores esperados
    private static final String ORIGEN_SESSION = "SESSION";
    private static final String ORIGEN_NO_SESSION = "NO_SESSION";
    private static final String ACCION_REGISTRAR = "registrar";
    private static final String ACCION_LOGIN = "login";

    // Rutas JSP
    private static final String VISTA_ERROR = "JSP/error.jsp";
    private static final String VISTA_LOGIN = "JSP/login.jsp";
    private static final String VISTA_CREAR_USUARIO = "JSP/crearUsuario.jsp";
    private static final String VISTA_CREAR_USUARIO_NO_SESSION = "JSP/crearUsuario_NO_SESSION.jsp";

    // Redirecciones
    private static final String REDIR_LISTAR_REGISTROS = "/RegistroServlet?accion=listarRegistros";

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter(PARAM_ACCION);

        if (accion == null) {
            response.sendRedirect(VISTA_ERROR);
            return;
        }

        switch (accion) {
            case ACCION_REGISTRAR:
                registrarUsuario(request, response);
                break;
            case ACCION_LOGIN:
                loginUsuario(request, response);
                break;
            default:
                response.sendRedirect(VISTA_ERROR);
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String origenFormulario = request.getParameter(PARAM_ORIGEN);
        try {
            Usuario nuevoUsuario = usuarioService.construirUsuarioDesdeRequest(request);
            boolean exito = usuarioService.registrarUsuario(nuevoUsuario);

            if (exito) {
                request.setAttribute(ATTR_EXITO_REGISTRO, "Usuario registrado con éxito");
                if (ORIGEN_SESSION.equals(origenFormulario)) {
                    request.getRequestDispatcher(VISTA_CREAR_USUARIO).forward(request, response);
                } else if (ORIGEN_NO_SESSION.equals(origenFormulario)) {
                    request.getRequestDispatcher(VISTA_LOGIN).forward(request, response);
                } else {
                    enviarError(request, response, "Origen del formulario no reconocido", origenFormulario);
                }
            } else {
                enviarError(request, response, "El nombre de usuario ya existe.", origenFormulario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarError(request, response, "Se produjo un error inesperado al registrar el usuario.", origenFormulario);
        }
    }

    private void loginUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombre = request.getParameter(PARAM_NOMBRE_USUARIO);
            String contrasena = request.getParameter(PARAM_CONTRASENA_USUARIO);

            Usuario usuario = usuarioService.loginUsuario(nombre, contrasena);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute(ATTR_USUARIO_LOGUEADO, usuario);
                response.sendRedirect(request.getContextPath() + REDIR_LISTAR_REGISTROS);
            } else {
                enviarErrorLogin(request, response, "Credenciales incorrectas. Inténtalo nuevamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarErrorLogin(request, response, "Error inesperado durante el login.");
        }
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje, String origenForm)
            throws ServletException, IOException {

        request.setAttribute(ATTR_ERROR_REGISTRO, mensaje);

        if (ORIGEN_SESSION.equals(origenForm)) {
            request.getRequestDispatcher(VISTA_CREAR_USUARIO).forward(request, response);
        } else if (ORIGEN_NO_SESSION.equals(origenForm)) {
            request.getRequestDispatcher(VISTA_CREAR_USUARIO_NO_SESSION).forward(request, response);
        } else {
            request.getRequestDispatcher(VISTA_LOGIN).forward(request, response);
        }
    }

    private void enviarErrorLogin(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute(ATTR_ERROR_LOGIN, mensaje);
        request.getRequestDispatcher(VISTA_LOGIN).forward(request, response);
    }
}
