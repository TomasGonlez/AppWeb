<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%
  Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

  if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/JSP/error1.jsp");
    return;
  }
  String tituloPagina = (String) request.getAttribute("tituloPagina");
  if (tituloPagina == null) {
    tituloPagina = "Gestor de Asistencia";
  }
%>

<!-- Navbar principal -->
<nav class="navbar navbar-expand-lg" style="background-color: #3498db;">
  <div class="container-fluid flex-column">
    <!-- Primera fila: navegación y sesión -->
    <div class="d-flex justify-content-between w-100 align-items-center">
      <a class="navbar-brand text-white fw-bold" href="<%= request.getContextPath() %>/JSP/inicio.jsp">Asistencia</a>
      <button class="navbar-toggler text-white border-white" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon" style="filter: invert(1);"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-between" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link text-white" href="<%= request.getContextPath() %>/JSP/resgistrar_entrada_salida.jsp">Registrar</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white" href="<%= request.getContextPath() %>/JSP/crearUsuario.jsp">Crear Usuario</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white" href="<%= request.getContextPath() %>/JSP/reportes.jsp">Reportes</a>
          </li>
        </ul>
        <div class="d-flex align-items-center">
                    <span class="text-white me-3">
                        Bienvenido, <strong><%= usuario.getNombreCompletoUser() %></strong>
                    </span>
          <a class="btn btn-outline-light" href="<%= request.getContextPath() %>/JSP/cerrarSesion.jsp">Cerrar sesión</a>
        </div>
      </div>
    </div>
</nav>
