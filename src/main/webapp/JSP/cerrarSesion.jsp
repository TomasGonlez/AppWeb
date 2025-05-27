<%--
  Created by IntelliJ IDEA.
  User: benja
  Date: 29-04-2025
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%
  session.invalidate(); // Cierra la sesión
  response.sendRedirect(request.getContextPath() + "/JSP/login.jsp");
%>
