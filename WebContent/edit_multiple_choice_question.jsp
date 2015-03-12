<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit Multiple Choice Question</title>
	</head>
	<body>
		<%
			QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
			int question_id = Integer.parseInt(request.getParameter("question_id"));
			MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
		%>
		<h1>Edit Multiple Choice Question</h1>
		<form action="QuestionEditServlet" method="post">
			<p>Include the actual answer in answer choices (the capitalizations should match). Separate the answer choices with commas (do not include , in the actual answer).</p>
			<p>Question: <br>
			<textarea rows="4" cols="50" name="prompt" ><%=question.getQuestionText() %></textarea></p>
			<p>Answer Choices: <br>
			<textarea rows="4" cols="50" name="choices" ><%=question.getChoicesAsText() %></textarea></p>
			<p>Answer: <br>
			<textarea rows="4" cols="50" name="answer" ><%=question.getAnswerText() %></textarea></p>
			<input type="hidden" name="ques_type" value="multiple_choice"/>
			<input type="hidden" name="quiz_id" value="<%=question.getQuizID() %>"/>
			<input type="hidden" name="question_id" value="<%=question_id %>"/>
			<input type="submit" name="update" value="Continue Editing"/>
		</form>
	</body>
</html>