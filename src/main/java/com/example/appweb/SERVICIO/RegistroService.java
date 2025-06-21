package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.MODELO.Persona;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.Usuario;
import com.example.appweb.UTIL.Errores;
import com.example.appweb.UTIL.RegistroUtils;
import com.example.appweb.UTIL.ValidadorFechas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistroService {
    private final PersonaService personaService;
    private final RegistroDAO registroDAO;

    public RegistroService(PersonaService personaService, RegistroDAO registroDAO) {
        this.personaService = personaService;
        this.registroDAO = registroDAO;
    }
    public boolean validarRegistroPersona(String rut){
        return registroDAO.ExistenciaRegistro(rut);
    }

    public void procesarRegistro(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws IOException, ServletException {


        String rut = request.getParameter("rutPersona");
        if(!this.personaService.validarRut(rut)) {
            Errores.enviarErrorIngresarPersona(request,response,"El rut ingresado no esta registrado, ¡Ve a registrar empleado primero!");
            return;
        }

        String nombre = request.getParameter("nombrePersona");
        if(!this.personaService.validarNombre(nombre, rut)){
            Errores.enviarErrorIngresarPersona(request,response,"El nombre no coincide con el rut.");
            return;
        }

        String tipo = request.getParameter("tipoRegistro");
        String fecha = request.getParameter("fechaPersona");
        String hora = RegistroUtils.obtenerHoraActual();

        try {
            ValidadorFechas.validarFechaNoFutura(fecha);
            ValidadorFechas.validarFechaPasada(fecha);
        } catch (IllegalArgumentException e) {
            Errores.enviarErrorIngresarPersona(request,response,e.getMessage());
            return;
        }
        if (this.personaService.validarRut(rut) && validarRegistroPersona(rut)) {
            procesarExistente(request, response, usuario, rut, tipo, fecha, hora);
        } else {
            procesarNuevo(request, response, usuario, rut, tipo, fecha, hora);
        }
    }
    private void procesarExistente(HttpServletRequest request, HttpServletResponse response, Usuario usuario,
                                   String rut, String tipo, String fecha, String hora)
            throws IOException, ServletException {
        String ultimoTipo = RegistroDAO.obtenerUltimoTipoRegistroGeneral(rut);

        if (ultimoTipo == null && !tipo.equals("INGRESO")) {
            Errores.enviarErrorIngresarPersona(request, response, "El primer acceso debe ser un INGRESO.");
            return;
        }

        if (ultimoTipo != null && ultimoTipo.equals(tipo)) {
            Errores.enviarErrorIngresarPersona(request, response, "No puedes registrar dos " + tipo + " consecutivos.");
            return;
        }

        if (tipo.equals("SALIDA")) {
            if (!RegistroUtils.validarCoherenciaFechas(registroDAO, rut, fecha,hora, request, response)) {
                return;
            }
        }
        Registro registro = RegistroUtils.crearRegistro(rut, usuario.getIdUsuario(), fecha, tipo, hora);
        registroDAO.registrar(registro);
        if (tipo.equals("INGRESO")) {
            request.setAttribute("exitoIngreso", "¡Se registro correctamente el "+ tipo+"!");
        }else{
            request.setAttribute("exitoIngreso", "¡Se registro correctamente la "+ tipo +"!");
        }
        request.getRequestDispatcher("JSP/registrar_entrada_salida.jsp").forward(request, response);
        //response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }

    private void procesarNuevo(HttpServletRequest request, HttpServletResponse response, Usuario usuario,
                               String rut, String tipo, String fecha, String hora)
            throws IOException, ServletException {

        if (!tipo.equals("INGRESO")) {
            Errores.enviarErrorIngresarPersona(request, response, "El primer registro debe ser un INGRESO.");
            return;
        }
        Registro registro = RegistroUtils.crearRegistro(rut, usuario.getIdUsuario(), fecha, tipo, hora);
        registroDAO.registrar(registro);
        request.setAttribute("exitoIngreso", "¡INGRESO registrado exitosamente!");
        request.getRequestDispatcher("JSP/registrar_entrada_salida.jsp").forward(request, response);
        //response.sendRedirect("JSP/registrar_entrada_salida.jsp");
    }
}
