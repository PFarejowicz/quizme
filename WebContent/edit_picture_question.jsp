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
		<title>Edit Picture Question</title>
	</head>
	<body>
		<%
			QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
			int question_id = Integer.parseInt(request.getParameter("question_id"));
			PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
		%>
		<h1>Edit Picture Question</h1>
		<p>Current Image:</p>
		<p><img src="<%=question.getQuestionText() %>"></p>
		<form action="QuestionEditServlet" method="post">
			<p>Enter a URL of an image.</p>
			<p>URL: <br>
			<textarea rows="4" cols="50" name="prompt" ><%=question.getQuestionText() %></textarea></p>
			<p>Answer: <br>
			<textarea rows="4" cols="50" name="answer" ><%=question.getAnswerText() %></textarea></p>
			<input type="hidden" name="ques_type" value="picture"/>
			<input type="hidden" name="quiz_id" value="<%=question.getQuizID() %>"/>
			<input type="hidden" name="question_id" value="<%=question_id %>"/>
			<input type="submit" name="update" value="Continue Editing"/>
		</form>
	</body>
</html>