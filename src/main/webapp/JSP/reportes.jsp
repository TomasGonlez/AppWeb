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
            <span class="text-muted"><%= new java.util.Date().toString() %></span>
        </div>

        <!-- Formulario optimizado para móviles -->
        <form class="row g-2 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
            <input type="hidden" name="accion" value="listar">
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
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>RUT</th>
                    <th>Nombre</th>
                    <th>Tipo Registro</th>
                    <th>Fecha</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<RegistroPersona> registros = (List<RegistroPersona>) request.getAttribute("registros");
                    if(registros != null){
                        for (RegistroPersona r :  registros){
                %>
                <tr>
                    <td data-label="RUT"><%=r.getRut()%></td>
                    <td data-label="NOMBRE"><%=r.getNombre()%></td>
                    <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
                    <td data-label="FECHA"><%=r.getFecha()%></td>
                </tr>
                <%}
                }else {
                %>
                <tr><td colspan="4" class="text-center">No se encontraron registros</td></tr>
                <%}%>
                </tbody>
            </table>
        </div>

        <h2 class="text-center mb-4">Reporte de Personas</h2>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <strong>Personas en las dependencias</strong>
            <span class="text-muted"><%= new java.util.Date().toString() %></span>
        </div>

        <!-- Formulario optimizado para móviles -->
        <form class="row g-2 mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="post">
            <input type="hidden" name="accion" value="listarDependencias">
            <div class="col-12 col-md-2">
                <button type="submit" class="btn btn-primary w-100 btn-sm">Generar Personas en las Dependencias</button>
            </div>
        </form>
        <!-- Tabla responsive -->
        <div class="table-responsive">
            <table class="table">
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
                    List<RegistroPersona> regDependencia = (List<RegistroPersona>) request.getAttribute("registrosDependencia");
                    if(regDependencia != null){
                        for (RegistroPersona r :  regDependencia){
                %>
                <tr>
                    <td data-label="NOMBRE"><%=r.getNombre()%></td>
                    <td data-label="RUT"><%=r.getRut()%></td>
                    <td data-label="FECHA"><%=r.getFecha()%></td>
                    <td data-label="TIPO REGISTRO"><%=r.getTipoRegistro()%></td>
                    <td data-label="HORA"><%=r.getHora()%></td>
                </tr>
                <%}
                }else {
                %>
                <tr><td colspan="5" class="text-center">No se encontraron registros</td></tr>
                <%}%>
                </tbody>
            </table>
        </div>

        <!-- Botones de exportación -->
        <div class="d-grid gap-2 d-md-flex justify-content-md-center mt-3">
            <a href="<%=request.getContextPath()%>/ExportarPDFServlet" class="btn btn-primary btn-sm me-md-2">Exportar PDF</a>
            <a href="<%=request.getContextPath()%>/ExportarExcelServlet" class="btn btn-primary btn-sm">Exportar EXCEL</a>
        </div>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>