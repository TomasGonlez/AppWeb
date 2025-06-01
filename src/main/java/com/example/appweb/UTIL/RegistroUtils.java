package com.example.appweb.UTIL;

import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.DAO.ReporteDAO;
import com.example.appweb.MODELO.Registro;
import com.example.appweb.MODELO.RegistroPersona;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistroUtils {
    public static Registro crearRegistro(String rut, int idUsuario, String fecha, String tipoRegistro, String hora) {
        Registro registro = new Registro();
        registro.setRut(rut);
        registro.setIdUsuario(idUsuario);
        registro.setFecha(Date.valueOf(fecha));
        registro.setTipoRegistro(tipoRegistro);
        registro.setHora(hora);
        return registro;
    }
    public static String obtenerFechaActualFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"));
        return LocalDate.now().format(formatter);
    }

    public static List<RegistroPersona> obtenerTodosLosRegistros() {
        RegistroDAO registroDAO = new RegistroDAO();
        return registroDAO.obtenerRegistros();
    }
    public static Map<String, Object> obtenerMetricasDelSistema() {
        ReporteDAO reporteDAO = new ReporteDAO();
        Map<String, Object> metricas = new HashMap<>();

        metricas.put("porcentajeAsistencia", reporteDAO.obtenerPorcentajeAsistenciaHoy());
        metricas.put("totalPersonas", reporteDAO.personasSistema());
        metricas.put("totalUsuarios", reporteDAO.usuariosSistema());
        metricas.put("personaDependencias", reporteDAO.dependenciasSistema());

        return metricas;
    }
    public static void configurarAtributosVista(HttpServletRequest request,
                                          List<RegistroPersona> registros,
                                          Map<String, Object> metricas,
                                          String fechaFormateada) {
        request.setAttribute("listaRegistros", registros);
        request.setAttribute("porcentajeAsistencia", metricas.get("porcentajeAsistencia"));
        request.setAttribute("totalPersonas", metricas.get("totalPersonas"));
        request.setAttribute("totalUsuarios", metricas.get("totalUsuarios"));
        request.setAttribute("personaDependencias", metricas.get("personaDependencias"));
        request.setAttribute("fechaActual", fechaFormateada);
    }

    public static void redirigirAVista(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String vista)
            throws ServletException, IOException {
        request.getRequestDispatcher(vista).forward(request, response);
    }
    public static String obtenerHoraActual() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    public static boolean validarCoherenciaFechas(RegistroDAO dao, String rut, String fechaSalidaStr,String horaSalidaStr,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            Date fechaSalida = Date.valueOf(fechaSalidaStr);
            Date fechaIngreso = dao.obtenerUltimaFechaIngreso(rut);

            if (fechaIngreso == null) {
                enviarError(request, response, "No hay un INGRESO registrado para este usuario.");
                return false;
            }

            if (fechaSalida.before(fechaIngreso)) {
                enviarError(request, response, "La SALIDA no puede ser antes del último INGRESO (" + fechaIngreso + ")");
                return false;
            }

            if (fechaSalida.equals(fechaIngreso)) {
                String horaIngreso = dao.obtenerUltimaHoraIngreso(rut);
                if (horaIngreso == null || horaSalidaStr == null) {
                    enviarError(request, response, "No se pudo obtener la hora de ingreso o salida.");
                    return false;
                }
                //Comparar las horas
                if(horaSalidaStr.compareTo(horaIngreso) < 0) {
                    enviarError(request,response,"La hora de SALIDA no puede ser anterior a la de INGRESO (" + horaIngreso + ")");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            enviarError(request, response, "Error al validar fechas: " + e.getMessage());
            return false;
        }
    }
    public static void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws IOException, ServletException {
        request.setAttribute("errorLogin", mensaje);
        request.getRequestDispatcher("JSP/registrar_entrada_salida.jsp").forward(request, response);
    }
}
