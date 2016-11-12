<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>_Dashboard Form</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
<%@include file="dependencies.jsp"%>

</head>

<body>

	<div id="loginForm" class="container">
		<center>
			<h1>Welcome to FabFlix</h1>
			<h1 class="pageHeading">Employee Login Form</h1>
			<hr>
			<form action="_dashboard" method="POST">
				<div class="form-group">
					<label><h4>Email:</h4></label> <input class="form-control"
						type="email" name="email" placeHolder="Email..." />
				</div>
				<div class="form-group">
					<label><h4>Password:</h4></label> <input class="form-control"
						type="password" name="password" placeHolder="Password..." />
				</div>
				<br>
				<div class="g-recaptcha" data-sitekey="6Lf14hcTAAAAANuZ_87zPddCiJ-ediuKCixggFDS"></div>
				<br>
				<button class="ghost-button-full-color">
				LOGIN
				</button>
			</form>
		</center>
	</div>
	<%
		String param = request.getParameter("submit");
		String recaptchaParam = request.getParameter("recaptcha");
		if (param != null && param.equals("fail")) {
	%>
	<center>
		<p class="alert alert-danger">The email and password entered was
			invalid.</p>
	</center>
	<%
		}
		else if (recaptchaParam != null && recaptchaParam.equals("fail")){
	%>
	<center>
		<p class="alert alert-danger">Incorrect recaptcha.</p>
	</center>
	<%} %>
</body>
</html>
