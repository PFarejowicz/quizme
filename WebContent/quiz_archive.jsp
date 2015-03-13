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
		<title>Search Results</title>
	</head>
	<body>
		<h1>QuizMe Quiz Finder Search Results</h1>
		<p class="auth-center"><a href="quiz_search.jsp"><button type="button">New Search</button></a>
		<a href="homepage.jsp"><button type="button">Return Home</button></a></p>
		<% QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
		ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("search"); %>
		<% if(quizList.size() > 0){ %>
		<div class="card">
		<h5>Search Results Found:</h5>
		<% 
		out.println("<ul>");
		for(Quiz quiz: quizList){
			out.println("<li>"+quiz.getName());
			out.println("<a href=\"quiz_summary.jsp?quiz_id="+quiz.getQuizID()+"\"><button type=\"button\">View Quiz</button></a>");
		}
		out.println("</ul>");
		%>
		</div>
		<% } %>
	</body>
</html>