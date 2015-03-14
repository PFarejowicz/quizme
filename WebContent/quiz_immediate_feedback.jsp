<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
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
		<h1 class="auth-center">QuizMe Feedback</h1>
		<p class="auth-center">See your graded response for : <%=quiz.getName() %><p>
		<%
		if (request.getParameter("correct_answer").equals("true")) {
			out.println("<div class=\"feedback_correct\">");
			out.println("<h2 class=\"auth-center\">Correct Answer</h2>");
			out.println("</div>");
		} else {
			out.println("<div class=\"feedback_incorrect\">");
			out.println("<h2 class=\"auth-center\">Incorrect Answer</h2>");
			out.println("</div>");
		}
		int question_num = Integer.parseInt(request.getParameter("question_num"));
		boolean isQuizFinished = request.getParameter("is_quiz_finished").equals("true");
		if (isQuizFinished) {
			out.println("<form class=\"auth-center\" action=\"quiz_results.jsp\" method=\"post\">");
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\" />");
			if (!request.getParameter("practice_mode").equals("true")) {
				out.println("<input type=\"hidden\" name=\"score\" value=\""+request.getParameter("score")+"\" />");
				out.println("<input type=\"hidden\" name=\"time_elapsed\" value=\""+request.getParameter("time_elapsed")+"\" />");
				out.println("<input type=\"hidden\" name=\"time_taken\" value=\""+request.getParameter("time_taken")+"\" />");
			}
			out.println("<p><input type=\"submit\" value=\"Finish\" /></p>");
			out.println("</form>");
		} else {
			out.println("<form  class=\"auth-center\" action=\"quiz_multiple_page_view.jsp\" method=\"post\">");
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\" />");
			out.println("<input type=\"hidden\" name=\"question_num\" value=\""+question_num+"\" />");
			out.println("<input type=\"hidden\" name=\"practice_mode\" value=\""+request.getParameter("practice_mode").equals("true")+"\" />");
			out.println("<input type=\"hidden\" name=\"score\" value=\""+request.getParameter("score")+"\" />");
			out.println("<input type=\"hidden\" name=\"immediate_correction\" value=\""+request.getParameter("immediate_correction")+"\" />");
			out.println("<p><input type=\"submit\" value=\"Continue\" /></p>");
			out.println("</form>");
		}
		%>
	</body>
</html>