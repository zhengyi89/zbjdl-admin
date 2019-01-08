<%
String location = request.getContextPath() + "/loginout/showLogin";

response.resetBuffer();
response.setStatus(HttpServletResponse.SC_FOUND);
response.setHeader("Location", location);
%>