<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String email = (String) session.getAttribute("email");
	if(email != null){
		UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
		if(userManager.isAdmin(email)){
			RequestDispatcher dispatch = request.getRequestDispatcher("admin_homepage.jsp");
			dispatch.forward(request, response);
		} else{
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
			dispatch.forward(request, response);
		}
	}
%>
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Welcome to QuizMe</title>
	</head>
	<body>
		<h1 class="auth-center">Welcome to QuizMe</h1>
		<img alt="QuizMe Logo" src="images/quizme_logo_1.png" class="img-center">
		<p class="auth-center">Please log in with your user credentials</p>
		<form  class="auth-center" action="LoginServlet" method="post">
			<p>Email: <input type="text" name="email" /></p>
			<p>Password: <input type="text" name="password" /></p>
			<input type="submit" value="Login" />
			<a href="nonregistered_access.jsp"><button type="button">Login As Guest</button></a>
		</form>
		<p class="auth-center"><a href="create_account.html"><button type="button">Create New Account</button></a></p>
	</body>
</html>