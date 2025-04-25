<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 24-04-2025
  Time: 23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appweb.MODELO.Usuario" %>
<%
    Usuario user = (Usuario) request.getAttribute("usuarioEncontrado");
%>
<html>
<head>
    <title>Usuario Encontrado</title>
</head>
<body>
    <h2>Usuario Encontrado</h2>
    <p>ID: <%= user.getIdUsuario() %></p>
    <p>Nombre: <%= user.getNombreUser() %></p>
    <p>Contraseña: <%= user.getContrasena() %></p>
    <p>Fecha de Creación: <%= user.getFechaCreacion() %></p>
</body>
</html>