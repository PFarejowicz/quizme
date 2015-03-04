<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<%
QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
Quiz quiz = quizManager.getQuiz(quiz_id);
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
int sentinel = Integer.parseInt(request.getParameter("sentinel"));
if (question_num >= sentinel) {
	out.println("<form action=\"quiz_results.jsp\" method=\"post\">");
	out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\" />");
	out.println("<input type=\"hidden\" name=\"score\" value=\""+request.getParameter("score")+"\" />");
	out.println("<p><input type=\"submit\" value=\"Get Results\" /></p>");
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