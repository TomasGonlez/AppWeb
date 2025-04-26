<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 25-04-2025
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
Time: 0:14
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login de la aplicación</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/UsuarioServlet" method="post">
  <input type="hidden" name="accion" value="login">

  <label for="nombreUsuario">Nombre de usuario: </label><br>
  <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

  <label for="contrasenaUsuario">Contraseña: </label><br>
  <input type="text" id="contrasenaUsuario" name="contrasenaUsuario" required><br><br>

  <input type="submit" value="login">
</form>
</body>
</html>