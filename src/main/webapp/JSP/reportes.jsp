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

        <table class="table table-striped table-bordered text-center">
            <thead>
            <tr>
                <th>Rut</th>
                <th>Nombre</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <%-- Esto se llenará dinámicamente desde el servlet --%>
            <c:forEach var="registro" items="${registros}">
                <tr>
                    <td>${registro.rut}</td>
                    <td>${registro.nombre}</td>
                    <td>${registro.accion}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="d-flex justify-content-center gap-3 mt-4">
            <a href="<%=request.getContextPath()%>/ExportarPDFServlet" class="btn btn-primary">Exportar PDF</a>
            <a href="<%=request.getContextPath()%>/ExportarExcelServlet" class="btn btn-primary">Exportar EXCEL</a>
        </div>
    </section>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
