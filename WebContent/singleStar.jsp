<%@include file="checkSession.jsp"%>

<%@page import="java.util.*,com.Star, com.Movie"%>

<%
	String starid = request.getParameter("id"); 
	Star star = (Star)request.getAttribute("star");
	ArrayList<Movie> starsIn = (ArrayList<Movie>)request.getAttribute("starsIn");
%>

<%@include file="checkSession.jsp"%>

<html>
<head>
<title>Single Star Page</title>
<%@include file="dependencies.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div id="singleStarPage" class="container">
		<h1 class="text-center pageHeading">Star Details</h1>
		<div class="moviePanel">
			<div class="row">
				<div class="col-sm-3" style="text-align:center; width:60%">
					<%
						if (star.getPhotoUrl().equals("")) {
					%>
					<img height="200" width="150" src="img/default_poster.jpg" />
					<%
						} else {
					%>
					<img height="200" width="150" src=<%=star.getPhotoUrl()%>
						onerror="this.onerror=null; this.src='img/default_poster.jpg';" />
					<%
						}
					%>
				</div>
				<div class="col-sm-5" style="text-align:left; width:40%">
					<div>
						<label>Id:</label>
						<%=star.getId()%>
					</div>
					<div>
						<label>Name:</label>
						<%=star.getFirstName() + ", " + star.getLastName()%>
					</div>
					<div>
						<label>Date of Birth:</label>
						<%=star.getDob()%>
					</div>
					<div>
						<label>Stars In:</label> 
						<br>
						<%
							for (int i = 0; i < starsIn.size(); i++) {
								if (i == starsIn.size() - 1) {
						%>
						<span><a href =<%= "singleMovie?movieId="+starsIn.get(i).getId()%>> <%=starsIn.get(i).getTitle()%> </a></span>
						<%
							} else {
						%>
						<span><a href =<%= "singleMovie?movieId="+starsIn.get(i).getId()%>> <%=starsIn.get(i).getTitle()%> </a>,</span>
						<%
									}
										}
								%>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
