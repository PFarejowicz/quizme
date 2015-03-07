<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<%
QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
Quiz quiz = quizManager.getQuizByID(quiz_id);
out.println(quiz.getName());
%>
</title>
</head>
<body>
<h1><%=quiz.getName() %></h1>
<h2>
<%
if (request.getParameter("correct_answer").equals("true")) {
	out.println("Correct Answer");
} else {
	out.println("Incorrect Answer");
}
%>
</h2>
<%
int question_num = Integer.parseInt(request.getParameter("question_num"));
boolean isQuizFinished = request.getParameter("is_quiz_finished").equals("true");
System.out.println(isQuizFinished);
if (isQuizFinished) {
	out.println("<form action=\"quiz_results.jsp\" method=\"post\">");
	out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\" />");
	if (!request.getParameter("practice_mode").equals("true")) {
		out.println("<input type=\"hidden\" name=\"score\" value=\""+request.getParameter("score")+"\" />");
		out.println("<input type=\"hidden\" name=\"time_elapsed\" value=\""+request.getParameter("time_elapsed")+"\" />");
	}
	out.println("<p><input type=\"submit\" value=\"Finish\" /></p>");
	out.println("</form>");
} else {
	out.println("<form action=\"quiz_multiple_page_view.jsp\" method=\"post\">");
	out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\" />");
	out.println("<input type=\"hidden\" name=\"question_num\" value=\""+question_num+"\" />");
	out.println("<input type=\"hidden\" name=\"score\" value=\""+request.getParameter("score")+"\" />");
	out.println("<input type=\"hidden\" name=\"immediate_correction\" value=\""+request.getParameter("immediate_correction")+"\" />");
	out.println("<p><input type=\"submit\" value=\"Continue\" /></p>");
	out.println("</form>");
}
%>
</body>
</html>