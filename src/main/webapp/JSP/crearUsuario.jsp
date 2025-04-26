<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Nuevo Usuario</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- CSS Personalizado -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/crear_usuario_style.css">
</head>
<body>

<!-- Barra superior -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <!-- Botón hamburguesa -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuNavbar" aria-controls="menuNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Título centrado -->
        <span class="navbar-brand mx-auto">Crear Nuevo Usuario</span>
    </div>
</nav>

<!-- Formulario -->
<div class="container py-4">
    <form action="<%= request.getContextPath() %>/UsuarioServlet" method="post" class="formulario-usuario mx-auto">
        <div class="mb-3">
            <label for="nombreCompleto" class="form-label">Nombre Completo</label>
            <input type="text" class="form-control input-custom" id="nombreCompleto" name="nombreCompleto" placeholder="Ingresar nombre completo" required>
        </div>

        <div class="mb-3">
            <label for="correoElectronico" class="form-label">Correo Electronico</label>
            <input type="email" class="form-control input-custom" id="correoElectronico" name="correoElectronico" placeholder="Ingresar correo electrónico" required>
        </div>

        <div class="mb-3">
            <label for="numeroTelefonico" class="form-label">Numero Telefonico</label>
            <input type="text" class="form-control input-custom" id="numeroTelefonico" name="numeroTelefonico" placeholder="Ingresar número telefónico" required>
        </div>

        <div class="mb-3">
            <label for="nombreUsuario" class="form-label">Nombre Usuario</label>
            <input type="text" class="form-control input-custom" id="nombreUsuario" name="nombreUsuario" placeholder="Ingresar nombre usuario" required>
        </div>

        <div class="mb-3">
            <label for="contrasena" class="form-label">Contraseña</label>
            <input type="password" class="form-control input-custom" id="contrasena" name="contrasena" placeholder="Ingresar contraseña" required>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-guardar">Guardar Usuario</button>
        </div>
    </form>
</div>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
