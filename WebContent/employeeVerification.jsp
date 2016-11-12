<%
  // Allow access only if session exists
  if (session.getAttribute("employee") != "verified"){
    response.sendRedirect("_dashboard.jsp");
  }
%>