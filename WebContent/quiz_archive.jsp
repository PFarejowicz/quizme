<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>QuizMe Quiz Archive</title>
	</head>
	<body>
		<h1>QuizMe Quiz Archive</h1>
		<p>Available quizzes ranked in alphabetical order:</p>
		<% 
		QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
		ArrayList<Quiz> quizList = quizManager.getQuizzes();
		out.println("<ul>");
		for(Quiz quiz: quizList){
			out.println("<li>"+quiz.getName());
			out.println("<a href=\"quiz_summary.jsp?quiz_id="+quiz.getQuizID()+"\"><button type=\"button\">View Quiz</button></a>");
		}
		out.println("</ul>");
		%>
	</body>
</html>