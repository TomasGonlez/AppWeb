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
        <div class="toggle-container">
            <button id="toggleButton" class="toggle-button">INGRESO</button>
        </div>

        <form class="register-form" action="<%= request.getContextPath() %>/RegistroServlet" method="post" autocomplete="off" id="registroForm">
            <input type="hidden" name="accion" value="ingresar">
            <input type="hidden" name="idUser" value="<%=usuario.getIdUsuario()%>">

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

            <div class="mb-3">
                <label for="fechaPersona" class="form-label"> Ingresar Fecha:</label>
                <input
                        type="date"
                        class="form-control modern-date"
                        id="fechaPersona"
                        name="fechaPersona"
                        required
                >
            </div>
            <button type="submit" class="register-button">Ingresar Asistencia</button>
            <input type="hidden" id="tipoRegistro" name="tipoRegistro" value="INGRESO">
        </form>
    </section>
</div>
<!-- Toast de error -->
<% if (request.getAttribute("errorIngresar") != null) { %>
<div id="toastError" class="toast toast-error show">
    <%= request.getAttribute("errorIngresar") %>
</div>
<% } %>

<% if (request.getAttribute("exitoIngreso") != null) { %>
<div id="toastExito" class="toast toast-success show">
    <%= request.getAttribute("exitoIngreso") %>
</div>
<% } %>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/JS/validaciones/ingresarEmpleado_form.js"></script>
</body>
</html>