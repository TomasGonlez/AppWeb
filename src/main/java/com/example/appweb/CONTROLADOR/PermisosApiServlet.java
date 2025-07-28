package com.example.appweb.CONTROLADOR;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/permisos")
public class PermisosApiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            out.print("{\"status\":\"error\", \"mensaje\":\"No autenticado\"}");
            out.flush();
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) session.getAttribute("permisos");
        if (permisos == null) {
            out.print("{\"status\":\"ok\", \"permisos\":[]}");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"status\":\"ok\", \"permisos\":[");
            for (int i = 0; i < permisos.size(); i++) {
                sb.append('"').append(permisos.get(i)).append('"');
                if (i < permisos.size() - 1) sb.append(",");
            }
            sb.append("]}");
            out.print(sb.toString());
        }
        out.flush();
    }
}
