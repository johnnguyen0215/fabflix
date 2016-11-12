<%@include file="checkSession.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Advanced Search</title>

<%@include file="dependencies.jsp"%>

</head>

<body>
	
	<%@include file="navbar.jsp"%>

	<div id="loginBox">
		<center>
			<h1 id="loginTitle">Advanced Search</h1>
		</center>
		<hr>
		<br>
		<form id="loginForm" action="search" method="GET">
			<table align="center" id="searchTable" border="0" cellpadding="1"
				cellspacing="1" style="width: 10%;">
				<tbody>
					<tr>
						<td><span><h4>Title:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="title"></td>
					</tr>
					<tr>
						<td><span><h4>Year:</h4></span></td>
					</tr>
					<tr>
						<td><select name="year">
								<option disabled selected>-- select year --</option>
								<script>
									var date = new Date();
									var year = date.getFullYear();
									for ( var i = year; i >= 1900; i--) {
										document
												.write('<option value="' + i + '">'
														+ i + '</option>');
									}
								</script>
						</select></td>
					</tr>
					<tr>
						<td><span><h4>Director:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="director"></td>
					</tr>
					<tr>
						<td><span><h4>Star First Name:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="f_n"></td>
					</tr>
					<tr>
						<td><span><h4>Star Last Name:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="l_n"></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="order" value="t_asc" /> 
			<input type="hidden" name="rpp" value="5" />
			<input type="hidden" name="page" value="1" /> 
			<div class="text-center" id="resetSubmit">
				<span>
					<a href="searchForm.jsp" class="btn btn-primary" type="submit" value="reset">Reset</a>
				</span> <span>
					<button class="btn btn-success" type="submit" value="search">Submit</button>
				</span>
			</div>
			
		  	<%
		
		  	String param = request.getParameter("submit");
		
		  	if (param != null && param.equals("fail")){
		  %>
	  			<p class="text-center alert alert-danger">Invalid search parameters</p>
		  <%}%>
		</form>
		
		
	</div>

</body>
</html>
