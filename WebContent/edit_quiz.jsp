<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit Quiz</title>
	</head>
	<body>
		<%
			QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
			AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager"));
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			adminManager.clearHistoryForQuiz(quiz_id);
			int user_id = (Integer) session.getAttribute("user id");
			Quiz quiz = quizManager.getQuizByID(quiz_id);
		%>
		<h1>Edit Quiz</h1>
		<form action="QuizCreationServlet" method="post">
			<p>Name: <input type="text" name="name" value="<%=quiz.getName()%>"/></p>
			<p>Description: <br><textarea rows="4" cols="50" name="description"><%=quiz.getDescription()%></textarea></p>
			<p>Random order: <br>
			<input type="radio" name="random_order" value="yes" <% if (quiz.isRandomOrder()) out.println("checked"); %>/> Yes <br>
			<input type="radio" name="random_order" value="no" <% if (!quiz.isRandomOrder()) out.println("checked"); %>/> No 
			</p>
			<p>Multiple pages: <br>
			<input type="radio" name="multiple_pages" value="yes" <% if (quiz.isMultiplePages()) out.println("checked"); %>/> Yes <br>
			<input type="radio" name="multiple_pages" value="no" <% if (!quiz.isMultiplePages()) out.println("checked"); %>/> No 
			</p>
			<p>Immediate correction: <br>
			<input type="radio" name="immediate_correction" value="yes" <% if (quiz.isImmediateCorrection()) out.println("checked"); %>/> Yes <br>
			<input type="radio" name="immediate_correction" value="no" <% if (!quiz.isImmediateCorrection()) out.println("checked"); %>/> No 
			</p>
			<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
			<input type="hidden" name="edit_mode" value="true"/>
			<input type="submit" value="Continue Editing" />
		</form>
	</body>
</html>