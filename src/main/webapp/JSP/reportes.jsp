<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%@ page import="com.example.appweb.MODELO.RegistroPersona" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appweb.MODELO.Registro" %>
<%@ page import="java.text.SimpleDateFormat" %>

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/reportes_style.css">
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="navbar.jsp" />
<div class="container-fluid flex-grow-1 p-0">
    <main class="container py-4">
        <!-- Sección Reporte por Fechas -->
        <section class="report-section mb-5">
            <div class="section-header d-flex justify-content-between align-items-center mb-4">
                <h2 class="section-title mb-0">
                    <i class="bi bi-calendar-range me-2"></i>Reporte por Rango de Fechas
                </h2>
                <span class="badge bg-primary">
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", new java.util.Locale("es", "ES"));
                        String fechaCompleta = sdf.format(new java.util.Date());
                        fechaCompleta = fechaCompleta.substring(0, 1).toUpperCase() + fechaCompleta.substring(1);
                    %>
                    <%= fechaCompleta %>
                </span>
            </div>

            <form class="row g-3 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
                <input type="hidden" name="accion" value="reporteFechas">
                <div class="col-md-3">
                    <label for="desde" class="form-label">Desde:</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                        <input type="date" id="desde" name="desde" class="form-control" required>
                    </div>
                </div>
                <div class="col-md-3">
                    <label for="hasta" class="form-label">Hasta:</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                        <input type="date" id="hasta" name="hasta" class="form-control" required>
                    </div>
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-funnel me-1"></i>Filtrar
                    </button>
                </div>
            </form>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <div class="table-responsive tabla-con-scroll">
                        <table id="tablaReportes" class="table table-hover mb-0">
                            <thead class="table-primary">
                                <tr>
                                    <th><i class="bi bi-person-vcard me-1"></i>RUT</th>
                                    <th><i class="bi bi-person me-1"></i>Nombre</th>
                                    <th><i class="bi bi-door-open me-1"></i>Tipo Registro</th>
                                    <th><i class="bi bi-calendar me-1"></i>Fecha</th>
                                    <th><i class="bi bi-clock me-1"></i>Hora</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                if(registros != null) {
                                    if(!registros.isEmpty()) {
                                        for (RegistroPersona r : registros) {
                                            String rowClass = "registro-" + r.getTipoRegistro().toLowerCase();
                            %>
                            <tr class="<%= rowClass %>">
                                <td><%=r.getRut()%></td>
                                <td><%=r.getNombre()%></td>
                                <td>
                                        <span class="badge <%= r.getTipoRegistro().equalsIgnoreCase("INGRESO") ? "bg-success" : "bg-danger" %>">
                                            <%=r.getTipoRegistro()%>
                                        </span>
                                </td>
                                <td><%=r.getFecha()%></td>
                                <td><%=r.getHora()%></td>
                            </tr>
                            <%      }
                            } else {
                            %>
                            <tr>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted">
                                    <i class="bi bi-database-exclamation fs-4"></i><br>
                                    No hay datos para las fechas seleccionadas
                                </td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                            </tr>
                            <%  }
                            } else {
                            %>
                            <tr>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted">
                                    <i class="bi bi-database-exclamation fs-4"></i><br>
                                    Genere un reporte por fechas para ver datos
                                </td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <%
                if (registros != null && !registros.isEmpty()) {
            %>
            <div class="d-flex justify-content-end mt-3">
                <div class="btn-group" role="group">
                    <a href="<%=request.getContextPath()%>/ExportarPDFServlet" class="btn btn-outline-primary">
                        <i class="bi bi-file-earmark-pdf me-1"></i>Exportar PDF
                    </a>
                    <a href="<%=request.getContextPath()%>/ExportarEXCELServlet" class="btn btn-outline-success">
                        <i class="bi bi-file-earmark-excel me-1"></i>Exportar Excel
                    </a>
                </div>
            </div>
            <%
                }
            %>
        </section>

        <!-- Sección Reporte de Dependencias -->
        <section class="report-section">
            <div class="section-header d-flex justify-content-between align-items-center mb-4">
                <h2 class="section-title mb-0">
                    <i class="bi bi-building me-2"></i>Personas en Dependencias
                </h2>
                <span class="badge bg-primary">
                    <%= fechaCompleta %>
                </span>
            </div>

            <form class="row g-3 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
                <input type="hidden" name="accion" value="reporteDependencias">
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-arrow-clockwise me-1"></i>Actualizar
                    </button>
                </div>
            </form>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <div class="table-responsive tabla-con-scroll">
                        <table id="tablaDependencias" class="table table-hover mb-0">
                            <thead class="table-primary">
                            <tr>
                                <th><i class="bi bi-person me-1"></i>Nombre</th>
                                <th><i class="bi bi-person-vcard me-1"></i>RUT</th>
                                <th><i class="bi bi-calendar me-1"></i>Fecha</th>
                                <th><i class="bi bi-door-open me-1"></i>Tipo Registro</th>
                                <th><i class="bi bi-clock me-1"></i>Hora</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                if(regDependencia != null) {
                                    if(!regDependencia.isEmpty()) {
                                        for (RegistroPersona r : regDependencia) {
                                            String rowClass = "registro-" + r.getTipoRegistro().toLowerCase();
                            %>
                            <tr class="<%= rowClass %>">
                                <td><%=r.getNombre()%></td>
                                <td><%=r.getRut()%></td>
                                <td><%=r.getFecha()%></td>
                                <td>
                                        <span class="badge <%= r.getTipoRegistro().equalsIgnoreCase("INGRESO") ? "bg-success" : "bg-danger" %>">
                                            <%=r.getTipoRegistro()%>
                                        </span>
                                </td>
                                <td><%=r.getHora()%></td>
                            </tr>
                            <%      }
                            } else {
                            %>
                            <tr>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted">
                                    <i class="bi bi-database-exclamation fs-4"></i><br>
                                    No hay datos para las fechas seleccionadas
                                </td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                            </tr>
                            <%  }
                            } else {
                            %>
                            <tr>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted">
                                    <i class="bi bi-database-exclamation fs-4"></i><br>
                                    Genere un reporte por fechas para ver datos
                                </td>
                                <td class="text-center py-4 text-muted"></td>
                                <td class="text-center py-4 text-muted"></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </main>
</div>

<!-- Toast de error -->
<% if (request.getAttribute("errorLogin") != null) { %>
<div id="toastError" class="toast toast-error show">
    <div class="toast-body d-flex align-items-center">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <%= request.getAttribute("errorLogin") %>
    </div>
</div>
<% } %>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function () {
        // Configuración común para ambas tablas
        const commonOptions = {
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
            },
            responsive: true,
            dom: '<"top"f>rt<"bottom"lip><"clear">',
            pageLength: 10,
            order: [[3, 'desc'], [4, 'desc']], // Ordenar por fecha y hora descendente
            columnDefs: [
                { responsivePriority: 1, targets: 0 }, // Prioridad para RUT/Nombre
                { responsivePriority: 2, targets: 1 }, // Prioridad para Nombre/RUT
                { responsivePriority: 3, targets: 2 }  // Prioridad para Tipo Registro
            ]
        };

        // Inicializar DataTables
        $('#tablaReportes').DataTable(commonOptions);
        $('#tablaDependencias').DataTable(commonOptions);

        // Control de Toast
        const toast = document.getElementById('toastError');
        if (toast) {
            setTimeout(() => {
                toast.classList.remove('show');
            }, 5000);
        }
    });
</script>
</body>
</html>