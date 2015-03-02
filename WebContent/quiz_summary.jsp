<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<%
QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
Quiz quiz = quizManager.getQuiz(quiz_id);
out.println(quiz.getName());
%>
</title>
</head>
<body>
<h1>Welcome to a QuizMe Quiz :)</h1>
<p>Quiz Name:<%= quiz.getName() %></p>
<p>Quiz Description:<%= quiz.getDescription() %></p>
<p>Quiz Author:<%= quiz.getAuthorID() %></p>
<p>Questions Presented in Random Order?:<%= quiz.isRandomOrder() %></p>
<p>Questions Presented on Multiple Pages?:<%= quiz.isMultiplePages() %></p>
<p>Immediate Corrections Provided for Questions?:<%= quiz.isImmediateCorrection() %></p>
</body>
</html>