<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>QuizMe Quiz Finder</title>
	</head>
	<body>
		<h1 class="auth-center">QuizMe Quiz Finder</h1>
		<div class="card">
		<h5>Search Filters:</h5>
		<form action="QuizSearchServlet" method="post">
			<label class="quiz_search">Name: <input type="text" name="name"/></label>
			<label class="quiz_search">Category: <select name="category">
				<option value=""></option>
				<option value="animals">Animals</option>
				<option value="entertainment">Entertainment</option>
				<option value="fun">Fun</option>
				<option value="gaming">Gaming</option>
				<option value="geography">Geography</option>
				<option value="history">History</option>
				<option value="hobbies">Hobbies</option>
				<option value="literature">Literature</option>
				<option value="language">Language</option>
				<option value="miscellaneous">Miscellaneous</option>
				<option value="movies">Movies</option>
				<option value="music">Music</option>
				<option value="religion">Religion</option>
				<option value="science">Science</option>
				<option value="sports">Sports</option>
				<option value="television">Television</option>
				<option value="world">World</option>
			</select></label>
			<label class="quiz_search">Tag: <input type="text" name="tag"/></label>
			<input class="quiz_search" type="submit" value="Search" />
		</form>
		</div>
		<p class="auth-center"><a href="homepage.jsp"><button type="button">Return Home</button></a></p>
	</body>
</html>