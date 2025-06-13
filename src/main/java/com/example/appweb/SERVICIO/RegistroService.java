package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.RegistroUtils;
import com.example.appweb.UTIL.ValidadorFechas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistroService {
    private final PersonaDAO personaDAO;
    private final RegistroDAO registroDAO;

    public RegistroService(PersonaDAO personaDAO, RegistroDAO registroDAO) {
        this.personaDAO = personaDAO;
        this.registroDAO = registroDAO;
    }

    public void procesarRegistro(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws IOException, ServletException {

        String rut = request.getParameter("rutPersona");
        String nombre = request.getParameter("nombrePersona");
        String tipo = request.getParameter("tipoRegistro");
        String fecha = request.getParameter("fechaPersona");
        String hora = RegistroUtils.obtenerHoraActual();

        try {
            ValidadorFechas.validarFechaNoFutura(fecha);
            ValidadorFechas.validarFechaPasada(fecha);
        } catch (IllegalArgumentException e) {
            RegistroUtils.enviarError(request, response, e.getMessage());
            return;
        }

        boolean rutExiste = personaDAO.buscarRut(rut);
        boolean nombreCoincide = personaDAO.buscarNombre(nombre, rut);

        if (rutExiste) {
            procesarExistente(request, response, usuario, rut, nombre, tipo, fecha, hora, nombreCoincide);
        } else {
            procesarNuevo(request, response, usuario, rut, nombre, tipo, fecha, hora);
        }
    }

    private void procesarExistente(HttpServletRequest request, HttpServletResponse response, Usuario usuario,
                                   String rut, String nombre, String tipo, String fecha, String hora,
                                   boolean nombreCoincide)
            throws IOException, ServletException {

        if (!nombreCoincide) {
            RegistroUtils.enviarError(request, response, "El nombre ingresado no coincide con el rut del Sistema");
            return;
        }
        String ultimoTipo = registroDAO.obtenerUltimoTipoRegistroGeneral(rut);

        if (ultimoTipo == null && !tipo.equals("INGRESO")) {
            RegistroUtils.enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }

        if (ultimoTipo != null && ultimoTipo.equals(tipo)) {
            RegistroUtils.enviarError(request, response, "No puedes registrar dos " + tipo + " consecutivos.");
            return;
        }

        if (tipo.equals("SALIDA")) {
            if (!RegistroUtils.validarCoherenciaFechas(registroDAO, rut, fecha,hora, request, response)) {
                return;
            }
        }

        Registro registro = RegistroUtils.crearRegistro(rut, usuario.getIdUsuario(), fecha, tipo, hora);
        registroDAO.registrar(registro);
        response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }

    private void procesarNuevo(HttpServletRequest request, HttpServletResponse response, Usuario usuario,
                               String rut, String nombre, String tipo, String fecha, String hora)
            throws IOException, ServletException {

        if (!tipo.equals("INGRESO")) {
            RegistroUtils.enviarError(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }
        personaDAO.registrar(new Persona(rut, nombre));
        Registro registro = RegistroUtils.crearRegistro(rut, usuario.getIdUsuario(), fecha, tipo, hora);
        registroDAO.registrar(registro);
        response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }
}
