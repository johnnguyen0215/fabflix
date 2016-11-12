<%@include file="employeeVerification.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Advanced Search</title>

<%@include file="dependencies.jsp"%>
<%
	String metadata = (String) session.getAttribute("metadata");
%>

</head>

<body>
	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="main.jsp">FabFlix</a>
	    </div>
	
	  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	    <ul class="nav navbar-nav pull-right">
	    	<li><a href="logout.jsp">Logout</a></li>
	    </ul>
	  </div>   
	 </div>
	</nav>
	
	<div class="row">
			<%
			String submitParam = request.getParameter("submit");

			if (submitParam != null && submitParam.equals("fail")) {
			%>
			<p class="text-center alert alert-danger">The input provided was invalid</p>
			<%
				}
			%>
		</div>
	
	<div id="dashboard" class="container">
		<div class="row">
			<center>
				<div class="col-sm-6">
					<div class="row">
						<h1>Add Star</h1>
						<form role="form" action="dashboard" method="GET">
							<div class="form-group">
								<div class="dashboardForm">
									<input type="hidden" name="type" value="addStar"/>
									<label class="pull-left">Full Name:</label>
								</div>
								<input class="form-control" type="text" name="name"/>
								<div class="dashboardForm">
									<label class="pull-left">Date of Birth: </label>
									<select class="form-inline" name="month">
										<option disabled selected>mm</option>
										<script>
											for ( var i = 1; i <=12; i++) {
												if(i<10){
													document.write('<option value=0' + i + '>'
																+ i + '</option>');}
												else{
													document
													.write('<option value=' + i + '>'
															+ i + '</option>');
												}
											}
										</script>
									</select>
									<select class="form-inline" name="day">
										<option disabled selected>dd</option>
										<script>
											var date = new Date();
											var year = date.getFullYear();
											for ( var i = 1; i <= 31; i++) {
												if(i<10){
													document.write('<option value=0' + i + '>'
																+ i + '</option>');}
												else{
													document
													.write('<option value=' + i + '>'
															+ i + '</option>');
												}
											}
										</script>
									</select>
									<select class="form-inline" name="year">
										<option disabled selected>yy</option>
										<script>
											var date = new Date();
											var year = date.getFullYear();
											for ( var i = 1872; i <= 2016; i++) {
												document
														.write('<option value="' + i + '">'
																+ i + '</option>');
											}
										</script>
									</select>
								</div>
								<div class="dashboardForm">
									<label class="pull-left">Photo Url: </label>
									<input class="form-control" type="text" name="photo_url"/>
								</div>
								<div class="text-center" class="resetSubmit">
									<span> <a href="dashboard.jsp" class="btn btn-primary"
										type="submit" value="reset">Reset</a>
									</span> <span>
										<button type="submit" class="btn btn-success">Add Star</button>
									</span>
								</div>
							</div>
							<%
								String starParam = request.getParameter("success");
				
								if (starParam != null && starParam.equals("addStar")) {
							%>
							<p class="text-center alert alert-success">Star has been added.</p>
							<%
								}
							%>
						</form>
					</div>
					<div class="row">
						<div id="metadata">
							<center>
								<h1 id="loginTitle">Metadata</h1>
							</center>
							<hr>
							<br>
							<%=metadata%>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<h1>Add Movie</h1>
					<form role="form" action="dashboard" method="GET">
						<div class="form-group">
							<input type="hidden" name="type" value="addMovie"/>
							<div class="dashboardForm">
								<label class="pull-left">Title: </label>
								<input class="form-control" type="text" name="title"/>
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Year: </label>
								<select class="form-inline" name="movie_year">
									<option disabled selected>yy</option>
									<script>
											var date = new Date();
											var year = date.getFullYear();
											for ( var i = 1872; i <= 2016; i++) {
												document
														.write('<option value="' + i + '">'
																+ i + '</option>');
											}
										</script>
								</select>
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Director: </label>
								<input class="form-control" type="text" name="director">
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Banner URL: </label>
								<input class="form-control" type="text" name="banner_url"/>
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Trailer URL: </label>
								<input class="form-control" type="text" name="trailer_url"/>
							</div>
							<h1>Star Info</h1>
							<div class="dashboardForm">
								<label class="pull-left">First Name: </label>
								<input class="form-control" type="text" name="f_n"/>
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Last Name: </label>
								<input class="form-control" type="text" name="l_n"/>
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Date of Birth: </label>
								<select class="form-inline" name="month">
									<option disabled selected>mm</option>
									<script>
										for ( var i = 1; i <=12; i++) {
											if(i<10){
												document.write('<option value=0' + i + '>'
															+ i + '</option>');}
											else{
												document
												.write('<option value=' + i + '>'
														+ i + '</option>');
											}
										}
									</script>
								</select>
								<select class="form-inline" name="day">
									<option disabled selected>dd</option>
									<script>
										var date = new Date();
										var year = date.getFullYear();
										for ( var i = 1; i <= 31; i++) {
											if(i<10){
												document.write('<option value=0' + i + '>'
															+ i + '</option>');}
											else{
												document
												.write('<option value=' + i + '>'
														+ i + '</option>');
											}
										}
									</script>
								</select> 
								<select class="form-inline" name="year">
									<option disabled selected>yy</option>
									<script>
										var date = new Date();
										var year = date.getFullYear();
										for ( var i = 1872; i <= 2016; i++) {
											document
													.write('<option value="' + i + '">'
															+ i + '</option>');
										}
									</script>
								</select> 
							</div>
							<div class="dashboardForm">
								<label class="pull-left">Photo URL: </label>
								<input class="form-control" type="text" name="photo_url">
							</div>
							<h1>Genre Info</h1>
							<div class="dashboardForm">
								<label class="pull-left">Genre Name: </label>
								<input class="form-control" type="text" name="genre_name">
							</div>
							<div class="text-center" class="resetSubmit">
								<span> <a href="dashboard.jsp" class="btn btn-primary"
									type="submit" value="reset">Reset</a>
								</span> <span>
									<button type="submit" class="btn btn-success">Add Movie</button>
								</span>
							</div>
						</div>
						
						<%
							String movieParam = request.getParameter("success");
			
							if (movieParam != null && movieParam.equals("addMovie")) {
						%>
						<p class="text-center alert alert-success">Movie has been added</p>
						<%
							}
						%>
					</form>
				</div>
			</center>
		</div>
	</div>
</body>
</html>
