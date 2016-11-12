<%@include file="checkSession.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Checkout: Customer Information</title>

<%@include file="dependencies.jsp"%>

</head>

<body>
	<%@include file="navbar.jsp"%>
	
	<div id="cInfoBox">
		<h1 id="cInfoTitle">Checkout: Customer Information</h1>
		<hr>
		<br>
		<form id="cInfoForm" action="customerInfo" method="POST">
			<table align="center" id="cInfoTable" border="0" cellpadding="1"
				cellspacing="1" style="width: 10%;">
				<tbody>
					<tr>
						<td><span><h4>First Name:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="first_name"></td>
					</tr>
					<tr>
						<td><span><h4>Last Name:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="last_name"></td>
					</tr>
					<tr>
						<td><span><h4>Credit Card Number:</h4></span></td>
					</tr>
					<tr>
						<td><input type="text" name="cc_n"></td>
					</tr>
					<tr>
						<td><span><h4>Credit Card Expiration:</h4></span></td>
					</tr>
					<tr>
						<td>
							<select name="month">
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
							<select name="day">
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
							<select name="year">
									<option disabled selected>yy</option>
									<script>
										var date = new Date();
										var year = date.getFullYear();
										for ( var i = 2000; i <= 2043; i++) {
											document
													.write('<option value="' + i + '">'
															+ i + '</option>');
										}
									</script>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="order" value="t_asc" /> 
			<input type="hidden" name="rpp" value="5" />
			<input type="hidden" name="page" value="1" /> 
			<div class="text-center" id="resetSubmit">
				<span>
					<a href="creditCardForm.jsp" class="btn btn-primary">Reset</a>
				</span> <span>
					<button class="btn btn-success" type="submit" value="search">Submit</button>
				</span>
			</div>
		</form>
		
		  <%
		
		  	String param = request.getParameter("submit");
		
		  	if (param != null && param.equals("fail")){
		  %>
	  			<p class="text-center alert alert-danger">The credit card information entered was invalid.</p>
		  <%}%>
		
	</div>

</body>
</html>
