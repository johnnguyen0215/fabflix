<%
  // Allow access only if session exists
  if (session.getAttribute("customerId") != null){
    response.sendRedirect("main.jsp");
  }

%>