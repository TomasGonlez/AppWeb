<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
  <div class="container-fluid">
    <!-- Logo y botón toggler -->
    <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/RegistroServlet?accion=listarRegistros">Principal</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
            aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Contenido colapsable -->
    <div class="collapse navbar-collapse" id="navbarContent">
      <!-- Menú de navegación -->
<ul class="navbar-nav me-auto mb-2 mb-lg-0">
  <%-- Obtener el rol del usuario desde la base de datos --%>
  <% 
    com.example.appweb.DAO.UsuarioDAO usuarioDAO = new com.example.appweb.DAO.UsuarioDAO();
    String rol = usuarioDAO.obtenerRolPorIdUsuario(usuario.getIdUsuario());
    if ("ADMIN".equals(rol)) { 
  %>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/JSP/crearUsuario.jsp">Registrar Usuario</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/JSP/registrar_empleado.jsp">Registrar Empleado</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/JSP/registrar_entrada_salida.jsp">Asistencia</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/ReporteServlet">Reportes</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/RegistroServlet?accion=listarRegistros">Dashboards</a>
    </li>
  <% } else if ("USUARIO".equals(rol)) { %>
    <li class="nav-item">
      <a class="nav-link" href="<%= request.getContextPath() %>/JSP/registrar_entrada_salida.jsp">Registrar Asistencia</a>
    </li>
  <% } %>
</ul>

      <!-- Información de usuario y logout -->
      <div class="d-flex flex-column flex-lg-row align-items-start align-items-lg-center gap-2 user-section">
        <span class="text-white">
          Bienvenido, <strong><%= usuario.getNombreCompletoUser() %></strong>
        </span>
        <a class="btn btn-outline-light btn-sm logout-btn" href="<%= request.getContextPath() %>/JSP/cerrarSesion.jsp">Cerrar sesión</a>
      </div>
    </div>
  </div>
</nav>

<!-- Referencia al archivo CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/navbar.css">