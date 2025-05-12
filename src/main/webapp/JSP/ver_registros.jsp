<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <!-- Viewport esencial para responsive -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>Registros Generales</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="<%= request.getContextPath() %>/CSS/reportes_style.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="navbar.jsp"/>

<!--<div class="container container-custom">
  <h5 class="mb-3">Registro de accesos recientes</h5>-->
<div class="container-fluid flex-grow-1 p-0">
  <main class="container py-3">
    <h2 class="text-center mb-4">Registro de Ingreso Diario</h2>

    <!-- Tabla de registros -->
    <div class="table-responsive">
      <table class="table">
        <thead>
        <tr>
          <th>RUT</th>
          <th>NOMBRE</th>
          <th>TIPO REGISTRO</th>
          <th>FECHA</th>
        </tr>
        </thead>
        <tbody>
        <%
          List<RegistroPersona> registros = (List<RegistroPersona>) request.getAttribute("listaRegistros");
          if(registros != null){
            for (RegistroPersona r :  registros){
        %>
        <tr>
          <td data-label="RUT"><%=r.getRut()%></td>
          <td data-label="NOMBRE"><%=r.getNombre()%></td>
          <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
          <td data-label="FECHA"><%=r.getFechaHora()%></td>
        </tr>
        <%}
        }else {
        %>
        <tr><td colspan="4" class="text-center">No se encontraron registros</td></tr>
        <%}%>
        </tbody>
      </table>
    </div>

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
  </main>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
