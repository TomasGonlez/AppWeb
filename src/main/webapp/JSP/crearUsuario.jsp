<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Nuevo Usuario</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/crear_usuario_style.css">

</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="main-wrapper">
    <section class="content">
        <form class="login-form" action="<%= request.getContextPath() %>/UsuarioServlet" method="post" autocomplete="off" id="formUsuario">
            <input type="hidden" name="accion" value="registrar">
            <input type="hidden" name="origen" value="SESSION">

            <!-- Nombre Completo -->
            <div class="mb-3">
                <label for="nombreCompletoUser" class="form-label">Nombre Completo</label>
                <input type="text" class="form-control" id="nombreCompletoUser" name="nombreCompletoUser"
                       placeholder="Ingresar nombre completo" required
                       minlength="5" maxlength="60"
                       pattern="[A-Za-záéíóúÁÉÍÓÚñÑ\s]{5,}"
                       title="Mínimo 5 caracteres alfabéticos">
                <div class="invalid-feedback">
                    El nombre debe tener al menos 5 letras (sin números ni caracteres especiales)
                </div>
            </div>

            <!-- Correo Electrónico -->
            <div class="mb-3">
                <label for="correoUser" class="form-label">Correo Electrónico</label>
                <input type="email" class="form-control" id="correoUser" name="correoUser"
                       placeholder="ejemplo@dominio.com" required
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                       title="Ingrese un correo válido (ejemplo@dominio.com)">
                <div class="invalid-feedback">
                    Por favor ingrese un correo electrónico válido
                </div>
            </div>

            <!-- Número Telefónico -->
            <div class="mb-3">
                <label for="numeroUser" class="form-label">Número Telefónico</label>
                <input type="tel" class="form-control" id="numeroUser" name="numeroUser"
                       placeholder="9 1234 5678" required
                       minlength="8" maxlength="10"
                       pattern="[\+0-9\s]{9,12}"
                       title="Ingrese un número válido (8-10 dígitos)">
                <div class="invalid-feedback">
                    El teléfono debe tener entre 8 y 10 dígitos (puede incluir + al inicio)
                </div>
            </div>

            <!-- Nombre Usuario -->
            <div class="mb-3">
                <label for="nombreUser" class="form-label">Nombre Usuario</label>
                <input type="text" class="form-control" id="nombreUser" name="nombreUser"
                       placeholder="Mínimo 4 caracteres" required
                       minlength="4" maxlength="15"
                       pattern="[A-Za-z0-9]{4,20}"
                       title="4-20 caracteres alfanuméricos (sin espacios)">
                <div class="invalid-feedback">
                    El nombre de usuario debe tener entre 4 y 15 caracteres alfanuméricos
                </div>
            </div>

            <!-- Contraseña -->
            <div class="mb-3">
                <label for="contrasena" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="contrasena" name="contrasena"
                       placeholder="Mínimo 8 caracteres" required
                       minlength="8"
                       pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                       title="Mínimo 8 caracteres, al menos una letra y un número y máximo 15 caracteres">
                <div class="invalid-feedback">
                    La contraseña debe tener al menos 8 caracteres, incluyendo letras y números y un máximo de 15 caracteres
                </div>
                <div class="form-text">
                    Mínimo 8 caracteres, al menos una letra y un número
                </div>
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
<script src="${pageContext.request.contextPath}/JS/validaciones/usuario_form.js" defer></script>
</body>
</html>