<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, java.util.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="jquery.rating.css">
	<script src="jquery.js"></script>
	<script src="jquery.rating.js"></script>
	<title>
	<%
	QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
	QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
	int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz quiz = quizManager.getQuizByID(quiz_id);
	out.println(quiz.getName() + " Results");
	%>
	</title>
</head>
<body>
	<h1><%=quiz.getName()%> Results</h1>
	<p>Score: <%=request.getParameter("score")%></p>
	<p>Time Elapsed: <%=request.getParameter("time_elapsed")%></p> 
	<form>
		<p>Review:</p>
		<textarea name="review" rows="5" cols="50"></textarea>
		<p>Rating:</p>
		<input type="radio" name="rating" value="1" class="star">
        <input type="radio" name="rating" value="2" class="star">
        <input type="radio" name="rating" value="3" class="star">
        <input type="radio" name="rating" value="4" class="star">
        <input type="radio" name="rating" value="5" class="star">
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>" />
        <input type="submit" name="finish" value="Finish" />
	</form>
</body>
</html>