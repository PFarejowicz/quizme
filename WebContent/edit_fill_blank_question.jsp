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
		<title>Edit Fill in the Blank Question</title>
	</head>
	<body>
		<%
			QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
			int question_id = Integer.parseInt(request.getParameter("question_id"));
			FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
		%>
		<h1>Edit Fill in the Blank Question</h1>
		<form action="QuestionEditServlet" method="post">
			<p>Enter the question and use * to represent a blank (do not include * in the actual question). Separate the answers with commas (do not include , in the actual answer).</p>
			<p>Question: <br>
			<textarea rows="4" cols="50" name="prompt" ><%=question.getQuestionRegex() %></textarea></p>
			<p>Answer: <br>
			<textarea rows="4" cols="50" name="answer" ><%=question.getAnswerAsText()%></textarea></p>
			<input type="hidden" name="ques_type" value="fill_blank"/>
			<input type="hidden" name="quiz_id" value="<%=question.getQuizID() %>"/>
			<input type="hidden" name="question_id" value="<%=question_id %>"/>
			<input type="hidden" name="prev_points" value="<%=question.getNumBlanks() %>"/>
			<input type="submit" name="update" value="Continue Editing"/>
		</form>
	</body>
</html>