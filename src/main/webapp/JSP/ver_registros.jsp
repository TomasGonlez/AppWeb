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

  <!-- TU CSS UNIFICADO (login_styles.css + CSS adicional) -->
  <link href="<%= request.getContextPath() %>/CSS/login_style.css" rel="stylesheet" />
</head>
<body>
<!-- Navbar con clases actualizadas -->
<jsp:include page="navbar.jsp"/>

<!-- Container principal con clase nueva -->
<div class="main-container">
  <div class="container py-4">

    <!-- Estadísticas rediseñadas -->
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
        <div class="estadistica-label">Porcentaje de Asistencia de los empleados para hoy <%=request.getAttribute("fechaActual")%></div>
        <div class="estadistica-value highlight"><%=request.getAttribute("porcentajeAsistencia")%></div>
      </div>
    </div>

    <!-- Título de tabla moderno -->
    <h2 class="tabla-titulo">Registros INGRESO/SALIDA Recientes</h2>

    <!-- Contenedor de tabla modernizado -->
    <div class="tabla-container">
      <div class="table-responsive">
        <table class="table tabla-data">
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
              for (RegistroPersona r : registros){
          %>
          <tr class="<%= r.getTipoRegistro().equals("SALIDA") ? "registro-salida" : "registro-ingreso" %>">
            <td data-label="RUT"><%=r.getRut()%></td>
            <td data-label="NOMBRE"><%=r.getNombre()%></td>
            <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
            <td data-label="FECHA"><%=r.getFecha()%></td>
            <td data-label="HORA"><%=r.getHora()%></td>
          </tr>
          <%}
          } else {
          %>
          <tr>
            <td colspan="5" class="text-center">No se encontraron registros</td>
          </tr>
          <%}%>
          </tbody>
        </table>
      </div>
    </div>

  </div>
</div>

<!-- Scripts -->
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
      pageLength: 10,
      language: {
        url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json' // Traducción al español
      },
      responsive: true
    });
  });
</script>
</body>
</html>