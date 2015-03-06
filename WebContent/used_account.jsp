<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Account Already in Use</title>
	</head>
	<body>
		<h1 class="auth-center">The email <%= request.getParameter("email") %> is already in use</h1>
		<p class="auth-center">Please enter another email and password.</p>
		<p class="auth-center"><a href="create_account.html"><button type="button">Go Back</button></a></p>
	</body>
</html>