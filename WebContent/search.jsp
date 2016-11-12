<%@include file="checkSession.jsp"%>

<%@page import="java.util.*,com.Movie"%>

<%
	ArrayList<Movie> movies = (ArrayList<Movie>) request
			.getAttribute("movies");
	String[] rppArr = (String[]) request.getAttribute("rppArr");
	String[] orderArr = (String[]) request.getAttribute("orderArr");

	String nextUrl = (String) request.getAttribute("nextUrl");
	String prevUrl = (String) request.getAttribute("prevUrl");

	String sortByHtml = (String) request.getAttribute("sortByHtml");
%>

<%@include file="checkSession.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Browse Page</title>
<%@include file="dependencies.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div id="searchPage" class="container">
		<%
			if (movies.size() == 0) {
		%>
		<center>
			<h1 class="pageHeading">No results were found</h1>
		</center>
		<%
			} else {
		%>
		<center>
			<h1 class="pageHeading">Search Results</h1>
			<hr>
		</center>
		<div id="orderDropdown" class="pull-right">
			<label id="sortLabel">Sort By: </label>
			<div class="dropdown">
				<button class="btn btn-default dropdown-toggle" type="button"
					id="orderDD" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true">
					<%=sortByHtml%><span class="caret"></span>
				</button>
				<ul class="dropdown-menu" aria-labelledby="orderDD">
					<li><a href=<%=orderArr[0]%>>Title Ascending <i
							class="fa fa-chevron-up"></i></a></li>
					<li><a href=<%=orderArr[1]%>>Title Descending <i
							class="fa fa-chevron-down"></i></a></li>
					<li><a href=<%=orderArr[2]%>>Year Ascending <i
							class="fa fa-chevron-up"></i></a></li>
					<li><a href=<%=orderArr[3]%>>Year Descending <i
							class="fa fa-chevron-down"></i></a></li>
				</ul>
			</div>
		</div>
		<ul>
			<%
				for (Movie movie : movies) {
			%>
			<li>
				<div class="moviePanel">
					<div class="row">
						<div class="col-sm-3">
							<%
								if (movie.getBannerUrl().equals("")) {
							%>
							<img height="200" width="150" src="img/default_poster.jpg" />
							<%
								} else {
							%>
							<img height="200" width="150" src=<%=movie.getBannerUrl()%>
								onerror="this.onerror=null; this.src='img/default_poster.jpg';" />
							<%
								}
							%>
						</div>
						<div class="col-sm-5">
							<div>
								<label>Title:</label> 
								<a class="titleLink" href=<%="singleMovie?movieId=" + movie.getId()%>><%=movie.getTitle()%></a>
								<div class="moviePopUp">
								</div>
							</div>
							<div>
								<label>Year:</label>
								<%=movie.getYear()%>
							</div>
							<div>
								<label>Director:</label>
								<%=movie.getDirector()%>
							</div>
							<div>
								<label>ID:</label>
								<%=movie.getId()%>
							</div>
							<!--  
							<form action="shoppingCart" method="GET">
								<input type="hidden" name="movieId" value=<%=movie.getId()%>>
								<input type="hidden" name="qty" value="1">
								<input type="hidden" name="type" value="add">
								<button type="submit" class="btn btn-success">
									<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
								</button>
							</form>-->
							<!-- 
							<i class="fa fa-info-circle fa-lg"></i>
							-->
							<button
								onClick="openCartWindow(<%=movie.getId()%>, '<%=movie.getTitle()%>')"
								class="btn btn-success">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</span>

						</div>
						<div class="col-sm-4">
							<div>
								<label>Stars:</label> <br>
								<%
									for (int i = 0; i < movie.getStars().size(); i++) {
								%>
								<%
									if (i == movie.getStars().size() - 1) {
								%>
								<span><a
									href=<%="singleStar?id="
									+ movie.getStars().get(i).getId()%>>
										<%=movie.getStars().get(i).getFullName()%>
								</a></span>
								<%
									} else {
								%>
								<span><a
									href=<%="singleStar?id="
									+ movie.getStars().get(i).getId()%>>
										<%=movie.getStars().get(i).getFullName()%>
								</a>,</span>
								<%
									}
											}
								%>
							</div>
							<div>
								<label>Genres:</label> <br>
								<%
									for (int i = 0; i < movie.getGenres().size(); i++) {
								%>
								<%
									if (i == movie.getGenres().size() - 1) {
								%>
								<span><a
									href="browse?by=genre&genre=<%=movie.getGenres().get(i)%>&page=1&order=t_asc&rpp=5"><%=movie.getGenres().get(i)%></a></span>
								<%
									} else {
								%>
								<span><a
									href="browse?by=genre&genre=<%=movie.getGenres().get(i)%>&page=1&order=t_asc&rpp=5"><%=movie.getGenres().get(i)%>,</a></span>
								<%
									}
											}
								%>
							</div>
						</div>
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
		<div class="text-center">
			<label class="resultsPerPage">Results Per Page:</label>
	
			
			<nav>
				<ul class="pagination">
					<li><a href=<%=prevUrl%> aria-label="Previous"><span
							aria-hidden="true">&laquo;</span>Previous
					</a></li>
					<li><a href=<%=rppArr[0]%>>5</a></li>
					<li><a href=<%=rppArr[1]%>>10</a></li>
					<li><a href=<%=rppArr[2]%>>15</a></li>
					<li><a href=<%=rppArr[3]%>>20</a></li>
					<li><a href=<%=rppArr[4]%>>25</a></li>
					<li><a href=<%=nextUrl%> aria-label="Next">Next <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>
		</div>
		<%
			}
		%>
	</div>
</body>
</html>
