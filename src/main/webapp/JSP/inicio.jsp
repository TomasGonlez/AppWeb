
<!DOCTYPE html>
<html>
<head>
    <title>Registros Generales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/CSS/inicio_styles.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container container-custom">
    <h5 class="mb-3">Registro de accesos recientes</h5>

    <!-- Tabla de registros -->
    <table class="registro-tabla">
        <thead>
        <tr>
            <th>RUT</th>
            <th>NOMBRE</th>
            <th>REGISTRO</th>
            <th>HORA</th>
            <th>FECHA</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

    <!-- Estadísticas -->
    <div class="estadisticas">
        <div>
            <div class="etiqueta">Personas registradas en el sistema</div>
            <div class="estadistica"></div>
        </div>
        <div>
            <div class="etiqueta">Usuarios registrados en el sistema</div>
            <div class="estadistica"></div>
        </div>
        <div>
            <div class="etiqueta">Porcentaje de asistencia total del personal</div>
            <div class="estadistica">%</div>
        </div>
    </div>
</div>

</body>
</html>
