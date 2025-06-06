<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Control de Acceso</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/login_style.css">
</head>
<body>
<div class="main-wrapper">
    <header class="header">
        <h2>Sistema Control de Acceso</h2>
    </header>

    <section class="content">
        <div class="logo-container">
            <div class="logo">Logo de la<br>Empresa</div>
        </div>

        <form class="login-form" action="<%= request.getContextPath() %>/UsuarioServlet" method="post" autocomplete="off" id="formLogin">
            <input type="hidden" name="accion" value="login">

            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Usuario</label>
                <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario"
                       placeholder="Ingresar usuario" autocomplete="off" required>
            </div>

            <div class="mb-3 input-group">
                <label for="contrasenaUsuario" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="contrasenaUsuario" name="contrasenaUsuario"
                       placeholder="Ingresar contraseña" autocomplete="off" required>
                <button type="button" id="togglePassword">
                    <i class="bi bi-eye"></i>
                </button>
            </div>
            <button type="submit" class="login-button">Iniciar Sesion</button>
        </form>

        <div class="create-account">
            <p>¿No tienes cuenta? Crea una <a href="<%= request.getContextPath() %>/JSP/crearUsuario_NO_SESSION.jsp">aquí</a></p>
        </div>
    </section>
</div>

<!-- Toasts/Notificaciones -->
<% if (request.getAttribute("exitoRegistro") != null) { %>
<div id="toastExito" class="toast toast-success show">
    <%= request.getAttribute("exitoRegistro") %>
</div>
<% } %>

<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastError" class="toast toast-error show">
    <%= request.getAttribute("errorLogin") %>
</div>
<% } %>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/JS/validaciones/login_form.js"></script>
</body>
</html>