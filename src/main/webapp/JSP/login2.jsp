<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Control de Acceso</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/login_style.css">
</head>
<body>
<div class="main-wrapper">
    <header class="header">
        <h2>Sistema de control de acceso</h2>
    </header>

    <section class="content">
        <div class="logo-container">
            <div class="logo">Logo de la<br>Empresa</div>
        </div>

        <form class="login-form" action="<%= request.getContextPath() %>/UsuarioServlet" method="post">
            <input type="hidden" name="accion" value="login">
            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Usuario</label>
                <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" placeholder="Ingresar usuario" required>
            </div>
            <div class="mb-3">
                <label for="contrasenaUsuario" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="contrasenaUsuario" name="contrasenaUsuario" placeholder="Ingresar contraseña" required>
            </div>
            <button type="submit" class="login-button">Iniciar Sesion</button>
        </form>

        <div class="create-account">
            <p> ¿No tienes cuenta? Crea una <a href="<%= request.getContextPath() %>/JSP/crearUsuario.jsp">aqui</a></p>
        </div>
        <div class="create-account">
            <p> IR A REGISTRO<a href="<%= request.getContextPath() %>/JSP/resgistrar_entrada_salida.jsp">aqui</a></p>
        </div>
    </section>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
