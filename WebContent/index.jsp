<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String email = (String) session.getAttribute("email");
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	if (email != null || userManager.getIDByEmail(email) != -1) {
		if (userManager.isAdmin(email)) {
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
		<div style="text-align:center">
			<div style="display:inline-block">
				<h1 class="auth-center">Welcome to QuizMe</h1><br>
				<p class="auth-center"><img alt="QuizMe Logo" src="images/quizme_logo_1.png"></p><br>
				<p class="auth-center">Please log in with your user credentials</p><br>
				<form class="auth-center" action="LoginServlet" method="post">
					<p class="auth-left">Email: <input class="field-right" type="text" name="email" /></p><br>
					<p class="auth-left">Password: <input class="field-right" type="text" name="password" /></p><br>
					<p><br></p>
					<input class="auth-center" type="submit" value="Login" /><br>
				</form><br>
				<p class="auth-center"><a class="auth-center" href="nonregistered_access.jsp"><button class="auth-center" type="button">Login As Guest</button></a></p><br>
				<p class="auth-center"><a class="auth-center" href="create_account.html"><button class="auth-center" type="button">Create New Account</button></a></p>
			</div>
		</div>
	</body>
</html>