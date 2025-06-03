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
            <input type="hidden" name="accion" value="registrar">
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
                <label for="fechaPersona" class="form-label">Ingresar Fecha:</label>
                <input type="date" class="form-control" id="fechaPersona" name="fechaPersona" required>
            </div>
            <button type="submit" class="register-button">Registrar</button>
            <input type="hidden" id="tipoRegistro" name="tipoRegistro" value="INGRESO">
        </form>
    </section>
</div>
<!-- Toast de error -->
<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastError" class="toast toast-error show">
    <%= request.getAttribute("errorLogin") %>
</div>
<% } %>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Toggle Button
    const toggleButton = document.getElementById('toggleButton');
    const tipoRegistro = document.getElementById('tipoRegistro');

    if (toggleButton && tipoRegistro) {
        toggleButton.addEventListener('click', function() {
            if (toggleButton.textContent === 'INGRESO') {
                toggleButton.textContent = 'SALIDA';
                toggleButton.classList.add('salida');
                tipoRegistro.value = 'SALIDA';
            } else {
                toggleButton.textContent = 'INGRESO';
                toggleButton.classList.remove('salida');
                tipoRegistro.value = 'INGRESO';
            }
        });
    }

    // Función mejorada para formatear y validar RUT
    function formatearYValidarRut(input) {
        // Formatear el RUT
        let valor = input.value.replace(/[^0-9kK]/g, '');

        if (valor.length > 9) {
            valor = valor.substring(0, 9);
        }

        if (valor.length === 0) {
            input.value = '';
            return;
        }

        let cuerpo = valor.slice(0, -1);
        let dv = valor.slice(-1).toUpperCase();

        let cuerpoFormateado = '';
        for (let i = cuerpo.length - 1, j = 1; i >= 0; i--, j++) {
            cuerpoFormateado = cuerpo[i] + cuerpoFormateado;
            if (j % 3 === 0 && i !== 0) {
                cuerpoFormateado = '.' + cuerpoFormateado;
            }
        }

        input.value = cuerpoFormateado + '-' + dv;

        // Validar longitud
        if (input.value.length < 11) {
            input.classList.add('is-invalid');
        } else {
            input.classList.remove('is-invalid');
        }
    }

    // Validación del formulario antes de enviar
    document.getElementById('registroForm').addEventListener('submit', function(event) {
        const rutInput = document.getElementById('rutPersona');

        // Validar longitud mínima del RUT
        if (rutInput.value.length < 11) {
            event.preventDefault();
            rutInput.classList.add('is-invalid');

            // Mostrar toast de error si es necesario
            mostrarError('El RUT debe tener al menos 11 caracteres (ej: 12.345.678-9)');
        }
    });

    // Función para mostrar errores
    function mostrarError(mensaje) {
        let toastContainer = document.getElementById('toastError');

        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.id = 'toastError';
            toastContainer.className = 'toast toast-error show';
            document.body.appendChild(toastContainer);
        }

        toastContainer.textContent = mensaje;
        toastContainer.classList.add('show');

        setTimeout(() => {
            toastContainer.classList.remove('show');
        }, 3000);
    }

    // Control de Toast existente
    document.addEventListener('DOMContentLoaded', function() {
        const toast = document.getElementById('toastError');
        if (toast) {
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000);
        }
    });
</script>
</body>
</html>