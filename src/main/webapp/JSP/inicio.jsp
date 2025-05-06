<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
<%@ page import="com.example.appweb.UTIL.ConexionDB" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registros Generales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/CSS/inicio_styles.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container container-custom">
    <h5 class="mb-3">Registro de accesos recientes</h5>

    <!-- Tabla de registros -->
    <table class="registro-tabla">
        <thead>
            <tr>
                <th>RUT</th>
                <th>NOMBRE</th>
                <th>REGISTRO</th>
            </tr>
        <%
            List<RegistroPersona> registros = (List<RegistroPersona>) request.getAttribute("listaRegistros");
            if(registros != null){
                for (RegistroPersona r :  registros){
        %>
        </thead>
        <tbody>
            <tr>
                <td><%=r.getRut()%></td>
                <td><%=r.getNombre()%></td>
                <td><%=r.getTipoRegistro()%></td>
            </tr>
        <%
            }}else{
        %>
        <tr><td colspan="3">No hay registros disponibles.</tr>
        <%
            }
        %>
        </tbody>
    </table>

    <!-- Estadísticas -->
    <div class="estadisticas">
        <div>
            <%
                int totalPersonas = 0;
                try {

                    Connection conn = ConexionDB.getInstance().getConexion();
                    String sql = "SELECT COUNT(*) AS total FROM PERSONA";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        totalPersonas = rs.getInt("total");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al consultar en la database: "+e.getMessage());
                }
            %>
            <div class="etiqueta">Personas registradas en el sistema</div>
            <div class="estadistica"><%=totalPersonas%></div>
        </div>
        <div>
            <%
                int totalUsuario = 0;
                try {
                    Connection conn = ConexionDB.getInstance().getConexion();
                    String sql = "SELECT COUNT(*) AS total FROM USUARIO";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        totalUsuario = rs.getInt("total");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al consultar en la database: "+e.getMessage());
                }
            %>
            <div class="etiqueta">Usuarios registrados en el sistema</div>
            <div class="estadistica"><%=totalUsuario%></div>
        </div>
        <div>
            <%
                int porcentaje = 0;
                try {

                    Connection conn = ConexionDB.getInstance().getConexion();
                    String sql = "SELECT COUNT(*) AS porcentaje FROM PERSONA";
                    String sql2 = "SELECT COUNT(p.rut) AS total FROM PERSONA p JOIN REGISTRO r ON p.rut = r.rut WHERE r.tipo_registro = 'INGRESO'";

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        totalPersonas = rs.getInt("total");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al consultar en la database: "+e.getMessage());
                }
            %>
            <div class="etiqueta">Porcentaje de asistencia total del personal</div>
            <div class="estadistica">%</div>
        </div>
    </div>
</div>

</body>
</html>
