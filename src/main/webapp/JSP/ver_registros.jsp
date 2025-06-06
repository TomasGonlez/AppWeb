<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <!-- Viewport esencial para responsive -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>Registros Generales</title>
  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- DataTables Bootstrap 5 CSS -->
  <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
  <link href="<%= request.getContextPath() %>/CSS/reportes_style.css" rel="stylesheet" />

</head>
<body>
<jsp:include page="navbar.jsp"/>

<!--<div class="container container-custom">
  <h5 class="mb-3">Registro de accesos recientes</h5>-->
<div class="container-fluid flex-grow-1 p-0">
  <main class="container py-3">
    <!-- Estadísticas -->
    <div class="estadisticas">
      <div class="estadistica-container">
        <div class="estadistica-label">Empleados registrados en el Gestor de Asistencia</div>
        <div class="estadistica-value"><%=request.getAttribute("totalPersonas")%></div>
      </div>

      <div class="estadistica-container">
        <div class="estadistica-label">Usuarios registrados en el Gestor de Asistencia</div>
        <div class="estadistica-value"><%=request.getAttribute("totalUsuarios")%></div>
      </div>

      <div class="estadistica-container">
        <div class="estadistica-label">Empleados que se encuentran en las dependencias</div>
        <div class="estadistica-value"><%=request.getAttribute("personaDependencias")%></div>
      </div>

      <div class="estadistica-container">
        <div class="estadistica-label">Porcentaje de Asistencia de los empleados para hoy <%=request.getAttribute("fechaActual")%> es </div>
        <div class="estadistica-value highlight"><%=request.getAttribute("porcentajeAsistencia")%></div>
      </div>

    <!-- Tabla de registros -->
    <h2 class="text-center mb-4">Tabla de Registros INGRESO/SALIDA recientes</h2>
    <div class="table-responsive tabla-con-scroll">
      <table class="table table-striped table-bordered tabla-data">
        <thead>
        <tr>
          <th>Rut</th>
          <th>Nombre</th>
          <th>Tipo Registro</th>
          <th>Fecha</th>
          <th>Hora</th>
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
          <td data-label="FECHA"><%=r.getFecha()%></td>
          <td data-label="HORA"><%=r.getHora()%></td>
        </tr>
        <%}
        }else {
        %>
        <tr><td colspan="5" class="text-center">No se encontraron registros</td></tr>
        <%}%>
        </tbody>
      </table>
    </div>



    </div>
  </main>
</div>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- DataTables core + Bootstrap 5 integration -->
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

<script>
  $(document).ready(function () {
    $('.tabla-data').DataTable({
      ordering: false,
      searching: true,
      pageLength: 5,
      language: {
        url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json' // Traducción al español
      }
    });
  });
</script>
</body>
</html>
