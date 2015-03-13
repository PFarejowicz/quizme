<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList" %>
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
		final int num_scores = 3;
		QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
		UserManager userManager = (UserManager) application.getAttribute("user manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = 0;
		if((Integer) session.getAttribute("user id") != null){
			user_id = (Integer) session.getAttribute("user id");
		}
		String email = (String) session.getAttribute("email");
		Quiz quiz = quizManager.getQuizByID(quiz_id);
		out.println(quiz.getName());
		%>
	</title>
</head>
<body>
	<h1>Welcome to a QuizMe Quiz</h1>
	<% if(email != null){ %>
		<% if(quizManager.checkQuizReported(quiz_id)){ %>
			<p class="reported">THIS QUIZ HAS BEEN REPORTED!</p>
		<% } else{ %>
			<form action="ReportQuizServlet" method="post">
				<input type="hidden" name="quiz_id" value="<%= quiz_id %>"/>
				<input type="submit" value="Report Quiz" style="color:red" />
			</form>
		<% } %>
	<% } %>
	<div class="card">
	<p>
		Quiz Name: <%=quiz.getName() %><br>
		Quiz Description: <%=quiz.getDescription() %> <br>
		Quiz Author: <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(quiz.getAuthorID())%>"><%=userManager.getNameByID(quiz.getAuthorID()) %></a><br>
		<%
		double rating = quizManager.calculateRating(quiz_id);
		if (rating < 0) {
			out.println("Quiz Rating: N/A");
		} else {
			out.println("Quiz Rating: " + String.format("%.2f", (float)rating));
		}
		%>
	</p>
	<p>
		Questions Presented in Random Order? <%= quiz.isRandomOrder() ? "Yes" : "No" %><br>
		Questions Presented on Multiple Pages? <%= quiz.isMultiplePages() ? "Yes" : "No" %><br>
		Immediate Corrections Provided for Questions? <%= quiz.isImmediateCorrection() ? "Yes" : "No" %>
	</p>
	</div>
	
	<% if(email != null){ %>
	<% ArrayList<QuizHistory> pastPerformance = quizManager.getQuizHistory(quiz_id, user_id, "past performance"); %>
	<% if(pastPerformance.size() > 0){ %>
	<div class="card">
	<h5>Your Past Performance</h5>
	<p><%
		int size = pastPerformance.size();
		for (int i = 0; i < size; i++) {
	        out.println("Date: " + pastPerformance.get(i).getTimeStamp() + " Score: " + pastPerformance.get(i).getScore());
	        out.println("<br>");
		}
	%></p>
	</div>
	<% } %>
	<% } %>
	
	<% ArrayList<QuizHistory> allTimeHigh = quizManager.getQuizHistory(quiz_id, user_id, "all time high"); %>
	<% if(allTimeHigh.size() > 0){ %>
	<div class="card">
	<h5>All Time High Scores</h5>
	<p><%
		int size = allTimeHigh.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + userManager.getNameByID(allTimeHigh.get(i).getUserId()) + " (" + quizManager.convertToPercStr(allTimeHigh.get(i).getScore(), allTimeHigh.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	</div>
	<% } %>
	
	<% ArrayList<QuizHistory> lastDayHigh = quizManager.getQuizHistory(quiz_id, user_id, "last day high"); %>
	<% if (lastDayHigh.size() > 0) { %>
	<div class="card">
	<h5>24 Hour High Scores</h5>
	<p><%
		int size = lastDayHigh.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + userManager.getNameByID(lastDayHigh.get(i).getUserId()) + " (" + quizManager.convertToPercStr(lastDayHigh.get(i).getScore(), lastDayHigh.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	</div>
	<% } %>
	
	<% ArrayList<QuizHistory> recentScores = quizManager.getQuizHistory(quiz_id, user_id, "recent"); %>
	<% if (recentScores.size() > 0) { %>
	<div class="card">
	<h5>Recent Scores</h5>
	<p><%
		int size = recentScores.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + userManager.getNameByID(recentScores.get(i).getUserId()) + " (" + quizManager.convertToPercStr(recentScores.get(i).getScore(), recentScores.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	</div>
	<% } %>
	
	<div class="card">
	<h5>Summary Statistics</h5>
	<p><%
		double avg = quizManager.avgQuizScore(quiz_id);
		if (avg < 0) {
			out.println("Average: N/A");
		} else {
			out.println("Average: " + quizManager.convertToPercStr(avg, quiz.getPoints()));
		}
		out.println("<br>");
		int range = quizManager.quizRange(quiz_id);
		if (range < 0) {
			out.println("Range: N/A");
		} else {
			out.println("Range: " + range);
		}
		out.println("<br>");
		out.println("Times taken: " + quizManager.numTimesTaken(quiz_id));
		out.println("<br>");
	%></p>
	</div>
	
	<% if (email != null) { %>
	<form action="QuizStartServlet" method="post" style="display: inline">
		<p>Mode: <br>
		<input type="radio" name="mode" value="regular" checked="checked"/> Regular <br>
		<input type="radio" name="mode" value="practice" /> Practice 
		</p>
		<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
		<input type="hidden" name="random_order" value="<%=quiz.isRandomOrder()%>"/>
		<input type="hidden" name="multiple_pages" value="<%=quiz.isMultiplePages()%>"/>
		<input type="hidden" name="immediate_correction" value="<%=quiz.isImmediateCorrection()%>"/>
		<input  type="submit" value="Take Quiz" />
	</form>
	
	<p><%
		if (user_id == quiz.getAuthorID()) {
			out.println("<h2>Author Toolbox</h2>");
			out.println("<form action=\"edit_quiz.jsp\" method=\"post\" style=\"display: inline\">");
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\"/>");
			out.println("<input type=\"hidden\" name=\"edit_mode\" value=\"true\"/>");
			out.println("<input type=\"submit\" value=\"Edit Quiz\" onclick=\"return confirm('WARNING: This operation will delete all prior quiz history for this quiz. Continue?')\"/>");
			out.println("</form>");
			out.println("<form action=\"AdminRemoveQuizServlet\" method=\"post\" style=\"display: inline\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+quiz_id+"\"/>");
			out.println("<input name=\"admin_edit\" type=\"hidden\" value=\"false\"/>");
			out.println("<input type=\"submit\" value=\"Delete Quiz\" onclick=\"return confirm('WARNING: This operation will permanently delete this quiz. Continue?')\"/>");
			out.println("</form>");
		}
	%></p>
	
	<a href="homepage.jsp"><button type="button">Return Home</button></a>
	<% } else{ %>
		<a href="nonregistered_access.jsp"><button type="button">Return Home</button></a>
	<% } %>
</body>
</html>