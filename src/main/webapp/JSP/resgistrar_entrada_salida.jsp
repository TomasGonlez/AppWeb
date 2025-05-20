<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        // No hay usuario en sesión, redirige a login
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
        <h1><%=usuario.getIdUsuario()%></h1>
        <div class="toggle-container">
            <button id="toggleButton" class="toggle-button">INGRESO</button>
        </div>

        <form class="register-form" action="<%= request.getContextPath() %>/RegistroServlet" method="post" autocomplete="off">
            <input type="hidden" name="accion" value="registrar">
            <input type="hidden" name="idUser" value="<%=usuario.getIdUsuario()%>">
            <div class="mb-3">
                <label for="rutPersona" class="form-label">Ingresar Rut:</label>
                <input type="text" class="form-control" id="rutPersona" name="rutPersona" placeholder="Ingresar rut" required oninput="formatearRut(this)">
            </div>
            <div class="mb-3">
                <label for="nombrePersona" class="form-label">Ingresar Nombre:</label>
                <input type="text" class="form-control" id="nombrePersona" name="nombrePersona" placeholder="Ingresar nombre" required>
            </div>
            <div class="mb-3">
                <label for="fechaPersona" class="form-label">Ingresar Fecha:</label>
                <input type="date" class="form-control" id="fechaPersona" name="fechaPersona" required>
            </div>
            <button type="submit" class="register-button" value="registrar">Registrar</button>
            <input type="hidden" id="tipoRegistro" name="tipoRegistro" value="INGRESO">
        </form>
        <%
            String errorLogin = (String) request.getAttribute("errorLogin");
            if (errorLogin != null) {
        %>
        <!-- TOAST flotante -->
        <div id="toast" class="toast show">
            <%= errorLogin %>
        </div>
        <script>
            // Hacer desaparecer el toast después de 3 segundos
            setTimeout(function() {
                var toast = document.getElementById('toast');
                if (toast) {
                    toast.classList.remove('show');
                }
            }, 3000);
        </script>
        <% } %>
    </section>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Script del Toggle -->
<script>
    const toggleButton = document.getElementById('toggleButton');
    const tipoRegistro = document.getElementById('tipoRegistro');

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
    function formatearRut(input) {
        let valor = input.value.replace(/\D/g, ''); // Elimina todo lo que no sea dígito

        if (valor.length === 0) {
            input.value = '';
            return;
        }

        let cuerpo = valor.slice(0, -1);
        let dv = valor.slice(-1);

        let cuerpoFormateado = '';
        let contador = 0;
        for (let i = cuerpo.length - 1; i >= 0; i--) {
            cuerpoFormateado = cuerpo[i] + cuerpoFormateado;
            contador++;
            if (contador % 3 === 0 && i !== 0) {
                cuerpoFormateado = '.' + cuerpoFormateado;
            }
        }

        input.value = cuerpoFormateado + '-' + dv;
    }
</script>
</body>
</html>
