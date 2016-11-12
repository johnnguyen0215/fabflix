<%@include file="checkSession.jsp"%>

<%@page import="java.util.*,com.Movie, com.Star"%>

<%
	Movie movieInfo = (Movie) request.getAttribute("movie");
	
%>

<%@include file="checkSession.jsp"%>

<html>
<head>
<title>Single Movie Page</title>
<%@include file="dependencies.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div id="singleMoviePage" class="container">
		<h1 class="text-center pageHeading">Movie Details</h1>
		<div class="moviePanel">
			<div class="row">
				<div class="col-sm-3">
					<!-- img height="200" width="150" src=<%=movieInfo.getBannerUrl()%> /-->
					<%
						if (movieInfo.getBannerUrl().equals("")) {
							System.out.println("no url");
					%>
					<img height="200" width="150" src="img/default_poster.jpg" />
					<%
						} else {
					%>
					<img height="200" width="150" src=<%=movieInfo.getBannerUrl()%>
						onerror="this.onerror=null; this.src='http://www.reelviews.net/resources/img/default_poster.jpg';" />
					<%
						}
					%>
				</div>
				<div class="col-sm-5">
					<div>
						<label>Title:</label>
						<%=movieInfo.getTitle()%>
					</div>
					<div>
						<label>Year:</label>
						<%=movieInfo.getYear()%>
					</div>
					<div>
						<label>Director:</label>
						<%=movieInfo.getDirector()%>
					</div>
					<div>
						<label>ID:</label>
						<%=movieInfo.getId()%>
					</div>
					<div>
						<!--  <label>Trailer:</label> -->
						<a href= <%=movieInfo.getTrailerUrl()%>> Watch the trailer </a>					
					</div>
					<button onClick="openCartWindow(<%=movieInfo.getId() %>, '<%=movieInfo.getTitle() %>')" class="btn btn-success"><i class="fa fa-cart-plus fa-lg"></i> Add to Cart</button></span>
				</div>
				<div class="col-sm-4">
					<div>
						<label>Stars:</label> <br>
						<%
									for (int i = 0; i < movieInfo.getStars().size(); i++) {
								%>
						<%
									if (i == movieInfo.getStars().size() - 1) {
								%>
						<span><a href =<%= "singleStar?id="+movieInfo.getStars().get(i).getId()%>> <%=movieInfo.getStars().get(i).getFullName()%> </a></span>
						<%
									} else {
								%>
						<span><a href =<%= "singleStar?id="+movieInfo.getStars().get(i).getId()%>> <%=movieInfo.getStars().get(i).getFullName()%> </a>,</span>
						<%
									}
										}
								%>
					</div>
					<div>
						<label>Genres:</label> <br>
						<%
									for (int i = 0; i < movieInfo.getGenres().size(); i++) {
								%>
						<%
									if (i == movieInfo.getGenres().size() - 1) {
								%>
						<span><a href="browse?by=genre&genre=<%=movieInfo.getGenres().get(i)%>&page=1&order=t_asc&rpp=5"><%=movieInfo.getGenres().get(i)%></a></span>
						<%
									} else {
								%>
						<span><a href="browse?by=genre&genre=<%=movieInfo.getGenres().get(i)%>&page=1&order=t_asc&rpp=5"><%=movieInfo.getGenres().get(i)%>,</a></span>
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
