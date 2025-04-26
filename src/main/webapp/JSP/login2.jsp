<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Control de Acceso</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/login_style.css">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-sm-12">
            <div class="login-container">
                <div class="header">
                    <h2>Sistema de control de acceso</h2>
                </div>

                <div class="logo-container">
                    <div class="logo">
                        Logo de la<br>Empresa
                    </div>
                </div>

                <form class="login-form" action="procesar_login.jsp" method="post">
                    <div class="mb-3">
                        <label for="usuario" class="form-label">Usuario</label>
                        <input type="text" class="form-control" id="usuario" name="usuario" placeholder="Ingresar usuario" required>
                    </div>
                    <div class="mb-3">
                        <label for="contrasena" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="contrasena" name="contrasena" placeholder="Ingresar contraseña" required>
                    </div>
                    <button type="submit" class="login-button">Iniciar Sesión</button>
                </form>
                <div class="create-account">
                    <p>¿No tienes cuenta? Crea una <a href="crear_usuario.jsp">aquí</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>