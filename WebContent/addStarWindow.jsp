<%@page import="java.util.*"%>


<%

	//HashMap<String,String> shoppingCart = (HashMap<String,String>)session.getAttribute("shoppingCart");
	//HashMap<String, String> movieTitles = (HashMap<String,String>)session.getAttribute("movieTitles");
	
	
	String f_n = request.getParameter("f_n");
	
	//movieTitle = movieTitle.replaceAll("%20", " ");
	
	String l_n = request.getParameter("l_n");
%>

<html>

<head>
	<title>Add Star Window</title>
	<%@include file="dependencies.jsp"%>
</head>

<body>

	<div id="addStarWindow" class="container">
		<p><%=f_n + " " + l_n%> has been added to your cart.</p>
		
	</div>
		

</body>

</html>

