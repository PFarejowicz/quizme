<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Account Already in Use</title>
	</head>
	<body>
		<div style="text-align:center">
			<div style="display:inline-block">
				<h1 class="auth-center">Please Try Again</h1><br>
				<p class="auth-center"><img alt="QuizMe Error" src="images/quizme_error.png" class="img-center"></p><br>
				<p class="auth-center">The email <%= request.getParameter("email") %> is already in use</p><br>
				<p class="auth-center">Please enter another email and password</p><br>
				<p class="auth-center"><a class="auth-center" href="create_account.html"><button class="auth-center" type="button">Go Back</button></a></p>
			</div>
		</div>
	</body>
</html>