<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 24-04-2025
  Time: 1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrar Nuevo Usuario</title>
</head>
<body>
    <h2>Formulario de Registro de Usuario</h2>

    <form action="<%= request.getContextPath() %>/UsuarioServlet" method="post">
        <label for="nombreUser">Nombre de Usuario:</label><br>
        <input type="text" id="nombreUser" name="nombreUser" required><br><br>

        <label for="contrasena">Contraseña:</label><br>
        <input type="password" id="contrasena" name="contrasena" required><br><br>

        <label for="fechaCreacion">Fecha de Creación:</label><br>
        <input type="date" id="fechaCreacion" name="fechaCreacion" required><br><br>

        <input type="submit" value="Registrar">
    </form>
</body>
</html>
