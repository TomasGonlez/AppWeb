<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 26-04-2025
  Time: 3:24
  To change this template use File | Settings | File Templates.
--%>
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
<div class="main-wrapper">
    <header class="header">
        <h2>Registrar Entrada/ Salida</h2>
    </header>

    <section class="content">
        <div class="toggle-section">
            <div class="toggle-description">
                <p>Usuario está registrando una entrada</p>
                <div class="toggle-buttons">
                    <button type="button" class="btn btn-primary active">ENTRADA</button>
                    <button type="button" class="btn btn-secondary">SALIDA</button>
                </div>
            </div>
            <div class="toggle-description">
                <p>Usuario está registrando una salida</p>
                <div class="toggle-buttons">
                    <button type="button" class="btn btn-secondary">ENTRADA</button>
                    <button type="button" class="btn btn-primary active">SALIDA</button>
                </div>
            </div>
        </div>

        <form class="register-form" action="<%= request.getContextPath() %>/RegistroServlet" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" id="rut" name="rut" placeholder="Ingresar rut" required>
            </div>
            <div class="mb-3">
                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingresar nombre" required>
            </div>
            <div class="mb-3">
                <input type="date" class="form-control" id="fecha" name="fecha" required>
            </div>
            <div class="mb-3">
                <input type="time" class="form-control" id="hora" name="hora" required>
            </div>
            <button type="submit" class="guardar-button">Guardar</button>
        </form>
    </section>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
