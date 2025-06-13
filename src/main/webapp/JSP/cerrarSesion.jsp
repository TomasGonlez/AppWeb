<%
  session.invalidate(); // Cierra la sesión
  response.sendRedirect(request.getContextPath() + "/JSP/login.jsp");
%>
