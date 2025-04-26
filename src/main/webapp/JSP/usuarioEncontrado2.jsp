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
    Usuario user = (Usuario) request.getAttribute("usuarioEncontrado2");
%>
<html>
<head>
    <title>El login del usuario se ha ejecutado correctamente</title>
</head>
<body>
    <h2>Usuario Logeado es el siguiente</h2>
    <p>ID del Usuario: <%= user.getIdUsuario() %></p>
    <p>Nombre Completo Usuario: <%= user.getNombreCompletoUser()%></p>
    <p>Correo electónico Usuario: <%=user.getCorreoUser()%></p>
    <p>Numero Telefónico Usuario: <%=user.getNumeroUser()%></p>
    <p>Nombre: <%= user.getNombreUser() %></p>
    <p>Contraseña: <%= user.getContrasena() %></p>
    <p>Fecha de Creación: <%= user.getFechaCreacion() %></p>
</body>
</html>