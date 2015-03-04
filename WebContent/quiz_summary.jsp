<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<%
QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
UserManager userManager = (UserManager) application.getAttribute("user manager");
int quiz_id = 7;
//Integer.parseInt(request.getParameter("quiz_id"));
Quiz quiz = quizManager.getQuiz(quiz_id);
out.println(quiz.getName());
%>
</title>
</head>
<body>
<h1>Welcome to a QuizMe Quiz</h1>
<p>Quiz Name: <%= quiz.getName() %></p>
<p>Quiz Description: <%= quiz.getDescription() %></p>
<p>Quiz Author: <%= userManager.getNameByID(quiz.getAuthorID()) %></p>
<p>Questions Presented in Random Order? <%= quiz.isRandomOrder() ? "Yes" : "No" %></p>
<p>Questions Presented on Multiple Pages? <%= quiz.isMultiplePages() ? "Yes" : "No" %></p>
<p>Immediate Corrections Provided for Questions? <%= quiz.isImmediateCorrection() ? "Yes" : "No" %></p>
<a href="homepage.jsp"><button type="button">Return Home</button></a>
<form action="QuizStartServlet" method="post" style="display: inline">
<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
<input type="hidden" name="random_order" value="<%=quiz.isRandomOrder()%>"/>
<input type="hidden" name="multiple_pages" value="<%=quiz.isMultiplePages()%>"/>
<input type="hidden" name="immediate_correction" value="<%=quiz.isImmediateCorrection()%>"/>
<input  type="submit" value="Take Quiz" />
</form>
</body>
</html>