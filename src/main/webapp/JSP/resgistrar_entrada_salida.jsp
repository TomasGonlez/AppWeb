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
        <div class="toggle-container">
            <button id="toggleButton" class="toggle-button">Entrada</button>
        </div>

        <form class="register-form" action="<%= request.getContextPath() %>/" method="post">
            <input type="hidden" name="accion" value="registrar">
            <div class="mb-3">
                <label for="rutPersona" class="form-label">Ingresar Rut:</label>
                <input type="text" class="form-control" id="rutPersona" name="rutPersona" placeholder="Ingresar rut" required>
            </div>
            <div class="mb-3">
                <label for="nombrePersona" class="form-label">Ingresar Nombre:</label>
                <input type="text" class="form-control" id="nombrePersona" name="nombrePersona" placeholder="Ingresar nombre" required>
            </div>
            <div class="mb-3">
                <label for="fechaPersona" class="form-label">Ingresar Fecha:</label>
                <input type="date" class="form-control" id="fechaPersona" name="fechaPersona" required>
            </div>
            <div class="mb-3">
                <label for="horaPersona" class="form-label">Ingresar Hora:</label>
                <input type="time" class="form-control" id="horaPersona" name="horaPersona" required>
            </div>
            <button type="submit" class="register-button">Registrar</button>
        </form>
    </section>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Script del Toggle -->
<script>
    const toggleButton = document.getElementById('toggleButton');
    const tipoRegistro = document.getElementById('tipoRegistro');

    toggleButton.addEventListener('click', function() {
        if (toggleButton.textContent === 'Entrada') {
            toggleButton.textContent = 'Salida';
            toggleButton.classList.add('salida');
            tipoRegistro.value = 'salida';
        } else {
            toggleButton.textContent = 'Entrada';
            toggleButton.classList.remove('salida');
            tipoRegistro.value = 'entrada';
        }
    });
</script>
</body>
</html>
