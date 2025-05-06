<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
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
                <th>FECHA</th>
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
                <td><%=r.getFechaHora()%></td>
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
            <div class="etiqueta">Personas registradas en el Gestor de Asistencia</div>
            <div class="estadistica"><%=request.getAttribute("totalPersonas")%></div>
        </div>
        <div>
            <div class="etiqueta">Usuarios registrados en el Gestor de Asistencia</div>
            <div class="estadistica"><%=request.getAttribute("totalUsuarios")%></div>
        </div>
        <div>
            <div class="etiqueta">Porcentaje de asistencia del personal para hoy <%=request.getAttribute("fechaActual")%> es:</div>
            <div class="estadistica"><%=request.getAttribute("porcentajeAsistencia")%></div>
        </div>
    </div>
</div>
</body>
</html>
