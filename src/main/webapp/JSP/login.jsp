<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Control de Acceso</title>

    <!-- Preconnect para optimizar carga de fuentes -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <!-- Estilos modernizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/login_style.css">

    <!-- Meta tags adicionales para SEO y PWA -->
    <meta name="description" content="Sistema de Control de Acceso - Iniciar Sesión">
    <meta name="theme-color" content="#667eea">
</head>
<body>
<div class="main-wrapper">
    <header class="header">
        <h2>Sistema Control de Acceso</h2>
    </header>

    <section class="content">
        <div class="logo-container">
            <div class="logo">
                Logo de la<br>Empresa
            </div>
        </div>

        <form class="login-form" action="<%= request.getContextPath() %>/UsuarioServlet" method="post" autocomplete="off" id="formLogin">
            <input type="hidden" name="accion" value="login">

            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Usuario</label>
                <input type="text"
                       class="form-control"
                       id="nombreUsuario"
                       name="nombreUsuario"
                       placeholder="Ingresa tu usuario"
                       autocomplete="username"
                       required
                       aria-describedby="nombreUsuario-help">
            </div>

            <div class="mb-3 input-group">
                <label for="contrasenaUsuario" class="form-label">Contraseña</label>
                <input type="password"
                       class="form-control"
                       id="contrasenaUsuario"
                       name="contrasenaUsuario"
                       placeholder="Ingresa tu contraseña"
                       autocomplete="current-password"
                       required
                       aria-describedby="contrasenaUsuario-help">
                <button type="button"
                        id="togglePassword"
                        aria-label="Mostrar/ocultar contraseña">
                    <i class="bi bi-eye" aria-hidden="true"></i>
                </button>
            </div>

            <button type="submit" class="login-button">
                Iniciar Sesión
            </button>
        </form>

        <div class="create-account">
            <p>¿No tienes cuenta? <a href="<%= request.getContextPath() %>/JSP/crearUsuario_NO_SESSION.jsp">Créala aquí</a></p>
        </div>
    </section>
</div>

<!-- Toasts/Notificaciones -->
<% if (request.getAttribute("exitoRegistro") != null) { %>
<div id="toastExito" class="toast toast-success show" role="alert" aria-live="polite">
    <%= request.getAttribute("exitoRegistro") %>
</div>
<% } %>

<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastError" class="toast toast-error show" role="alert" aria-live="polite">
    <%= request.getAttribute("errorLogin") %>
</div>
<% } %>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/JS/validaciones/login_form.js"></script>

<!-- Script para auto-ocultar toasts -->
<script>
    // Auto-ocultar toasts después de 5 segundos
    document.addEventListener('DOMContentLoaded', function() {
        const toasts = document.querySelectorAll('.toast.show');
        toasts.forEach(toast => {
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => toast.remove(), 300);
            }, 5000);
        });
    });
</script>
</body>
</html>