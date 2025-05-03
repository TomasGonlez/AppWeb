
<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%@ page import="com.example.appweb.MODELO.Registro" %>
<%@ page import="java.util.List" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/JSP/error1.jsp");
        return;
    }
    List<Registro> registros = (List<Registro>) request.getAttribute("registros");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/resgistro_style.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="main-wrapper">
    <section class="content">
        <h2 class="text-center mb-4">Reportes</h2>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <strong>Por rango de fechas</strong>
            <span class="text-muted">[Fecha actual]</span>
        </div>

        <form class="row g-2 align-items-center mb-4" action="<%=request.getContextPath()%>/ReporteServlet" method="get">
            <div class="col-auto">
                <label for="desde" class="col-form-label">Desde:</label>
            </div>
            <div class="col-auto">
                <input type="date" id="desde" name="desde" class="form-control" required>
            </div>
            <div class="col-auto">
                <label for="hasta" class="col-form-label">Hasta:</label>
            </div>
            <div class="col-auto">
                <input type="date" id="hasta" name="hasta" class="form-control" required>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">GENERAR</button>
            </div>
        </form>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>RUT</th>
                    <th>ID Usuario</th>
                    <th>Fecha</th>
                    <th>Tipo Registro</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (registros != null && !registros.isEmpty()) {
                        for (Registro reg : registros) {
                %>
                <tr>
                    <td><%= reg.getRut() %></td>
                    <td><%= reg.getIdUsuario() %></td>
                    <td><%= reg.getFechaHora() %></td>
                    <td><%= reg.getTipoRegistro() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr><td colspan="4">No se encontraron registros</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <div class="export-buttons">
            <a href="<%=request.getContextPath()%>/ExportarPDFServlet" class="btn btn-primary">Exportar PDF</a>
            <a href="<%=request.getContextPath()%>/ExportarExcelServlet" class="btn btn-primary">Exportar EXCEL</a>
        </div>
    </section>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
