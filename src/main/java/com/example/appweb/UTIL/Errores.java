package com.example.appweb.UTIL;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Errores {

    public static void enviarErrorRegistrarPersona(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws IOException, ServletException {
        request.setAttribute("errorPersona", mensaje);
        request.getRequestDispatcher("JSP/registrar_empleado.jsp").forward(request, response);
    }
    public static void enviarErrorIngresarPersona(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws IOException, ServletException {
        request.setAttribute("errorIngresar", mensaje);
        request.getRequestDispatcher("JSP/registrar_entrada_salida.jsp").forward(request, response);
    }
}
