<%@page import="java.util.*"%>

<%@include file="checkSession.jsp"%>

<%
	HashMap<String, String> cart = (HashMap<String, String>) session
			.getAttribute("shoppingCart");
	HashMap<String, String> movieTitles = (HashMap<String, String>) session
			.getAttribute("movieTitles");
%>

<html>
<head>
<title>Shopping Cart</title>

<%@include file="dependencies.jsp"%>

</head>

<body>

	<%@include file="navbar.jsp"%>

	<div id="shoppingCart" class="container">

		<h1 class="text-center pageHeading">Shopping Cart</h1>
		<hr>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Movie Title</th>
					<th>Quantity</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					for (String id : cart.keySet()) {
				%>
				<form action="shoppingCart" method="GET">
					<input type="hidden" name="movieId" value=<%=id%>>
					<tr>
						<td class="movieTitle"><%=movieTitles.get(id)%></td>
						<td><input type="text" name="qty" value=<%=cart.get(id)%>>
						</td>
						<td>
							<button class="btn btn-sm btn-primary" type="submit" name="type"
								value="update">Update</button>
						</td>
						<td>
							<button class="btn btn-sm btn-danger" type="submit" name="type"
								value="remove">Remove</button>
						</td>
					</tr>
				</form>
				<%
					}
				%>
			</tbody>
		</table>
		<div class="text-center">
			<form action="shoppingCart" method="GET">
				<span>
					<button style="width: 150px" class="ghost-button-full-color"
						type="submit" name="type" value="checkout">
						<i class="fa fa-credit-card"></i>CHECKOUT
					</button>
				</span> <span>
					<button class="ghost-button-full-color" type="submit" name="type"
						value="empty">
						<i class="fa fa-trash"></i>EMPTY
					</button>
				</span>
			</form>
		</div>

		<%
			String param = request.getParameter("submit");

			if (param != null && param.equals("fail")) {
		%>
		<p class="text-center alert alert-danger">Invalid input parameters.</p>
		<%
			}
		%>
	</div>
	
</body>
</html>
