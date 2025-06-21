<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.Registro" %>

<%
    // 1. Validación de usuario logueado (MANTENIDO)
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/JSP/error1.jsp");
        return;
    }
    // Obtener datos de AMBAS tablas de la sesión
    List<RegistroPersona> registros = (List<RegistroPersona>) session.getAttribute("registros");
    List<RegistroPersona> regDependencia = (List<RegistroPersona>) session.getAttribute("registrosDependencia");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <!-- Viewport esencial para responsive -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/reportes_style.css">
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="navbar.jsp" />
<div class="container-fluid flex-grow-1 p-0">
    <main class="container py-3">
        <h2 class="text-center mb-4">Reportes de Ingresos y Salidas</h2>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <strong>Por rango de fechas</strong>
            <%@ page import="java.text.SimpleDateFormat" %>
            <%
                // Formateador con locale español
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", new java.util.Locale("es", "ES"));
                String fechaCompleta = sdf.format(new java.util.Date());

                // Capitalizar solo la primera letra del string completo
                fechaCompleta = fechaCompleta.substring(0, 1).toUpperCase() + fechaCompleta.substring(1);
            %>
            <span class="text-muted"><%= fechaCompleta %></span>
        </div>

        <!-- Formulario optimizado para móviles -->
        <form class="row g-2 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
            <input type="hidden" name="accion" value="reporteFechas">
            <div class="col-12 col-sm-6 col-md-3">
                <label for="desde" class="form-label">Desde:</label>
                <input type="date" id="desde" name="desde" class="form-control form-control-sm" required>
            </div>
            <div class="col-12 col-sm-6 col-md-3">
                <label for="hasta" class="form-label">Hasta:</label>
                <input type="date" id="hasta" name="hasta" class="form-control form-control-sm" required>
            </div>
            <div class="col-12 col-md-2">
                <button type="submit" class="btn btn-primary w-100 btn-sm">GENERAR</button>
            </div>
        </form>
        <!-- Tabla responsive -->
        <div class="table-responsive tabla-con-scroll">
            <table class="table table-striped table-bordered tabla-data">
                <thead>
                <tr>
                    <th>RUT</th>
                    <th>Nombre</th>
                    <th>Tipo Registro</th>
                    <th>Fecha</th>
                    <th>Hora</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(registros != null) {
                        if(!registros.isEmpty()) {
                            for (RegistroPersona r : registros) {
                %>
                <tr>
                    <td data-label="RUT"><%=r.getRut()%></td>
                    <td data-label="NOMBRE"><%=r.getNombre()%></td>
                    <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
                    <td data-label="FECHA"><%=r.getFecha()%></td>
                    <td data-label="HORA"><%=r.getHora()%></td>
                </tr>
                <%      }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No hay datos para las fechas seleccionadas</td>
                </tr>
                <%  }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Genere un reporte por fechas para ver datos</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <!-- Botones de exportación -->
        <%
            // Botones de exportación solo si hay registros
            if (registros != null && !registros.isEmpty()) {
        %>
        <div class="d-grid gap-2 d-md-flex justify-content-md-center mt-3">
            <a href="<%=request.getContextPath()%>/ExportarPDFServlet" class="btn btn-primary btn-sm me-md-2">Exportar PDF</a>
            <a href="<%=request.getContextPath()%>/ExportarEXCELServlet" class="btn btn-primary btn-sm">Exportar EXCEL</a>
        </div>
        <%
            }
        %>
        <h2 class="text-center mb-4">Reporte de Personas</h2>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <strong>Personas en las dependencias</strong>
            <span class="text-muted"><%= fechaCompleta %></span>
        </div>

        <!-- Formulario optimizado para móviles -->
        <form class="row g-2 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
            <input type="hidden" name="accion" value="reporteDependencias">
            <div class="col-12 col-md-2">
                <button type="submit" class="btn btn-primary w-150 btn-sm">GENERAR</button>
            </div>
        </form>
        <!-- Tabla responsive -->
        <div class="table-responsive tabla-con-scroll">
            <table class="table table-striped table-bordered tabla-data">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Rut</th>
                    <th>Fecha</th>
                    <th>Tipo Registro</th>
                    <th>Hora</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(regDependencia != null) {
                        if(!regDependencia.isEmpty()) {
                            for (RegistroPersona r : regDependencia) {
                %>
                <tr>
                    <td data-label="NOMBRE"><%=r.getNombre()%></td>
                    <td data-label="RUT"><%=r.getRut()%></td>
                    <td data-label="FECHA"><%=r.getFecha()%></td>
                    <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
                    <td data-label="HORA"><%=r.getHora()%></td>
                </tr>
                <%      }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No hay personas en las dependencias</td>
                </tr>
                <%  }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Genere un reporte de dependencias para ver datos</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

    </main>
</div>
<!-- Toast de error -->
<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastError" class="toast toast-error show">
    <%= request.getAttribute("errorLogin") %>
</div>
<% } %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    $(document).ready(function () {
        $('.tabla-data').DataTable({
            ordering: true, // Permitir ordenamiento
            searching: true,
            pageLength: 10, // Mostrar más registros por defecto
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json' // Traducción al español
            },
            dom: '<"top"f>rt<"bottom"lip><"clear">' // Mejor disposición de controles
        });
    });
</script>
<script>
    // Control de Toast
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