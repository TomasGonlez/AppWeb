<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Nuevo Usuario</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/crear_usuario_style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="main-wrapper">
    <section class="content">
        <form class="login-form" action="<%= request.getContextPath() %>/UsuarioServlet" method="post" autocomplete="off">
            <input type="hidden" name="accion" value="registrar">
            <input type="hidden" name="origen" value="SESSION">
            <div class="mb-3">
                <label for="nombreCompletoUser" class="form-label">Nombre Completo</label>
                <input type="text" class="form-control" id="nombreCompletoUser" name="nombreCompletoUser"
                       placeholder="Ingresar nombre completo" required>
            </div>

            <div class="mb-3">
                <label for="correoUser" class="form-label">Correo Electrónico</label>
                <input type="email" class="form-control" id="correoUser" name="correoUser"
                       placeholder="Ingresar correo electrónico" required>
            </div>

            <div class="mb-3">
                <label for="numeroUser" class="form-label">Número Telefónico</label>
                <input type="text" class="form-control" id="numeroUser" name="numeroUser"
                       placeholder="Ingresar número telefónico" required>
            </div>

            <div class="mb-3">
                <label for="nombreUser" class="form-label">Nombre Usuario</label>
                <input type="text" class="form-control" id="nombreUser" name="nombreUser"
                       placeholder="Ingresar nombre usuario" required>
            </div>

            <div class="mb-3">
                <label for="contrasena" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="contrasena" name="contrasena"
                       placeholder="Ingresar contraseña" required>
            </div>

            <button type="submit" class="login-button">Guardar Usuario</button>
        </form>
    </section>
</div>

<!-- Toast para error de nombre de usuario -->
<% if (request.getAttribute("errorRegistroUsuario") != null) { %>
<div id="toastNombreUser" class="toast toast-error show">
    <%= request.getAttribute("errorRegistroUsuario") %>
</div>
<% } %>
<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastNombreUser" class="toast toast-error show">
    <%= request.getAttribute("errorLogin") %>
</div>
<% } %>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Control de Toast
    document.addEventListener('DOMContentLoaded', function() {
        const toast = document.getElementById('toastNombreUser');
        if (toast) {
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000);
        }
    });
</script>
</body>
</html>