<%@page import="java.util.*"%>


<%

	HashMap<String,String> shoppingCart = (HashMap<String,String>)session.getAttribute("shoppingCart");
	HashMap<String, String> movieTitles = (HashMap<String,String>)session.getAttribute("movieTitles");
	
	
	String movieTitle = request.getParameter("movieTitle");
	
	movieTitle = movieTitle.replaceAll("%20", " ");
	
	String movieId = request.getParameter("movieId");
	
	if (shoppingCart.containsKey(movieId)){
		int qty = Integer.valueOf(shoppingCart.get(movieId));
		String quantity = Integer.toString(qty+1);
		shoppingCart.put(movieId, quantity);
	}
	else{
		shoppingCart.put(movieId, "1");
		movieTitles.put(movieId,movieTitle);
	}
%>

<html>

<head>
	<title>Shopping Cart Window</title>
	<%@include file="dependencies.jsp"%>
</head>

<body>

	<div id="shoppingCartWindow" class="container">
		<p><%=movieTitle %> has been added to your cart.</p>
		
		<h4 class="text-center">Your Shopping Cart:</h4>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th> Movie Title </th>
					<th> Quantity </th>
				</tr>
			</thead>
			<tbody>
				<% for (String id : shoppingCart.keySet()) {%>
				<tr>
					<td><%=movieTitles.get(id) %></td>
					<td><%=shoppingCart.get(id) %></td>
				<%} %>
			</tbody>
		</table>
	</div>
		

</body>

</html>

