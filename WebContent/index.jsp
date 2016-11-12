<%

  if (session.getAttribute("customerId") == null){
    response.sendRedirect("login.jsp");
  }
  else{
	  response.sendRedirect("main.jsp");
  }
%>