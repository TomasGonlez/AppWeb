<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 24-04-2025
  Time: 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error al Registrar</title>
</head>
<body>
  <h2>❌ Ocurrió un error al registrar la persona en la base de datos.</h2>
  <p>Revisa los datos e intenta nuevamente.</p>
  <p><a href="<%=request.getContextPath()%>/JSP/login.jsp">Volver al login</a></p>

</body>
</html>
