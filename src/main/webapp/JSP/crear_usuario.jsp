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
        <input type="hidden" name="accion" value="registrar">
        <label for="nombreCompletoUser">Nombre Completo:</label><br>
        <input type="text" id="nombreCompletoUser" name="nombreCompletoUser" required><br><br>

        <label for="correoUser">Correo Electrónico: </label><br>
        <input type="text" id="correoUser" name="correoUser" required><br><br>

        <label for="numeroUser">Numero Telefónico: </label><br>
        <input type="number" id="numeroUser" name="numeroUser" required><br><br>

        <label for="nombreUser">Nombre de Usuario:</label><br>
        <input type="text" id="nombreUser" name="nombreUser" required><br><br>

        <label for="contrasena">Contraseña:</label><br>
        <input type="password" id="contrasena" name="contrasena" required><br><br>

        <label for="fechaCreacion">Fecha de Creación:</label><br>
        <input type="date" id="fechaCreacion" name="fechaCreacion" required><br><br>

        <input type="submit" value="Registrar">
    </form>


    <h2>Si desea logearse debe apretar el botón</h2>

    <input type="button" value="LOGIN" onclick="location.href='login2.jsp'">

    <h2>Buscar a usuario porr ID</h2>

    <form action="<%= request.getContextPath() %>/UsuarioServlet" method="post">
        <input type="hidden" name="accion" value="buscar">
        <label for="idUsuario">Ingresar ID de Usuario</label><br>
        <input type="number" id="idUsuario" name="idUsuario" required><br><br>
        <input type="submit" value="Buscar">
    </form>

</body>
</html>
