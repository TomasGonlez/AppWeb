<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/JSP/error1.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Entrada/Salida</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/resgistro_style.css">
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="main-wrapper">
    <section class="content">
        <form id="registroForm" class="register-form" action="<%=request.getContextPath()%>/PersonaServlet" method="post" autocomplete="off">
            <input type="hidden" name="accion" value="registrar">
            <div class="mb-3">
                <label for="rutPersona" class="form-label">Ingresar Rut:</label>
                <input type="text" class="form-control" id="rutPersona" name="rutPersona"
                       placeholder="Ej: 12.345.678-9" required
                       oninput="formatearYValidarRut(this)"
                       maxlength="12"
                       minlength="11"
                       pattern="^[0-9]{1,2}\.?[0-9]{3}\.?[0-9]{3}-[0-9kK]$"
                       title="El RUT debe tener formato 12.345.678-9 (mínimo 11 caracteres)">
                <div class="invalid-feedback" id="rutFeedback">
                    El RUT debe tener al menos 11 caracteres con formato (ej: 12.345.678-9)
                </div>
            </div>

            <div class="mb-3">
                <label for="nombrePersona" class="form-label">Ingresar Nombre:</label>
                <input type="text" class="form-control" id="nombrePersona" name="nombrePersona"
                       placeholder="Ingresar nombre" required>
            </div>
            <button type="submit" class="register-button">Registrar</button>
        </form>
    </section>
</div>
<!-- Toast de error -->
<% if (request.getAttribute("errorPersona") != null) { %>
<div id="toastError" class="toast toast-error show">
    <%= request.getAttribute("errorPersona") %>
</div>
<% } %>

<% if (request.getAttribute("exitoRegistro") != null) { %>
<div id="toastExito" class="toast toast-success show">
    <%= request.getAttribute("exitoRegistro") %>
</div>
<% } %>
<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/JS/validaciones/resgitrarEmpleado_form.js"></script>
</body>
</html>