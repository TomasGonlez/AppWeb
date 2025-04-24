package com.example.appweb.CONTROLADOR;

import com.example.appweb.DAO.UsuarioDAO;
import com.example.appweb.MODELO.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException{
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Leer los parametros del formulario
        String nombre = request.getParameter("nombreUser");
        String contrasena = request.getParameter("contrasena");
        String fechaStr = request.getParameter("fecha_creacion");

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreUser(nombre);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setFechaCreacion(fecha);

            boolean exito = usuarioDAO.guardar(nuevoUsuario);

            if(exito){
                response.sendRedirect("JSP/exito.jsp");
            }else{
                response.sendRedirect("JSP/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("JSP/error.jsp");
        }
    }
}
