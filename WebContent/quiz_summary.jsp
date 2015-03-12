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
	<p>
		Quiz Name: <%=quiz.getName() %><br>
		Quiz Description: <%=quiz.getDescription() %> <br>
		Quiz Author: <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(quiz.getAuthorID())%>"><%=userManager.getNameByID(quiz.getAuthorID()) %></a><br>
		Quiz Rating: <%=String.format("%.2f", (float)quizManager.calculateRating(quiz_id)) %>
	</p>
	<p>
		Questions Presented in Random Order? <%= quiz.isRandomOrder() ? "Yes" : "No" %><br>
		Questions Presented on Multiple Pages? <%= quiz.isMultiplePages() ? "Yes" : "No" %><br>
		Immediate Corrections Provided for Questions? <%= quiz.isImmediateCorrection() ? "Yes" : "No" %>
	</p>
	
	<% if(email != null){ %>
	<h2>Your Past Performance</h2>
	<p><%
		ArrayList<QuizHistory> pastPerformance = quizManager.getQuizHistory(quiz_id, user_id, "past performance");
		int size = pastPerformance.size();
		for (int i = 0; i < size; i++) {
	        out.println("Date: " + pastPerformance.get(i).getTimeStamp() + " Score: " + pastPerformance.get(i).getScore());
	        out.println("<br>");
		}
	%></p>
	<% } %>
	
	<h2>All Time High Scores</h2>
	<p><%
		ArrayList<QuizHistory> allTimeHigh = quizManager.getQuizHistory(quiz_id, user_id, "all time high");
		int size = allTimeHigh.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + allTimeHigh.get(i).getName() + " (" + quizManager.convertToPercStr(allTimeHigh.get(i).getScore(), allTimeHigh.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	
	<h2>24 Hour High Scores</h2>
	<p><%
		ArrayList<QuizHistory> lastDayHigh = quizManager.getQuizHistory(quiz_id, user_id, "last day high");
		size = lastDayHigh.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + lastDayHigh.get(i).getName() + " (" + quizManager.convertToPercStr(lastDayHigh.get(i).getScore(), lastDayHigh.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	
	<h2>Recent Scores</h2>
	<p><%
		ArrayList<QuizHistory> recentScores = quizManager.getQuizHistory(quiz_id, user_id, "recent");
		size = recentScores.size();
		for (int i = 0; i < size && i < num_scores; i++) {
	        out.println((i + 1) + ": " + recentScores.get(i).getName() + " (" + quizManager.convertToPercStr(recentScores.get(i).getScore(), recentScores.get(i).getTotal()) + ")");
	        out.println("<br>");
		}
	%></p>
	
	<h2>Summary Statistics</h2>
	<p><%
		double avg = quizManager.avgQuizScore(quiz_id);
		if (avg < 0) {
			out.println("Average: Insufficient data");
		} else {
			out.println("Average: " + quizManager.convertToPercStr(avg, quiz.getPoints()));
		}
		out.println("<br>");
		int range = quizManager.quizRange(quiz_id);
		if (range < 0) {
			out.println("Range: Insufficient data");
		} else {
			out.println("Range: " + range);
		}
		out.println("<br>");
		out.println("Times taken: " + quizManager.numTimesTaken(quiz_id));
		out.println("<br>");
	%></p>
	
	<% if(email != null){ %>
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
			out.println("<form action=\"edit_quiz.jsp\" method=\"post\">");
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+quiz_id+"\"/>");
			out.println("<input type=\"hidden\" name=\"edit_mode\" value=\"true\"/>");
			out.println("<input type=\"submit\" value=\"Edit Quiz\" onclick=\"return confirm('WARNING: This operation will delete all prior quiz history for this quiz. Continue?')\"/>");
			out.println("</form>");
			out.println("<form action=\"AdminRemoveServlet\" method=\"post\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+quiz_id+"\"/>");
			out.println("<input name=\"admin_edit\" type=\"hidden\" value=\"false\"/>");
			out.println("<input type=\"submit\" value=\"Delete Quiz\" onclick=\"return confirm('WARNING: This operation will delete all prior quiz history for this quiz. Continue?')\"/>");
			out.println("</form>");
		}
	%></p>
	
	<a href="homepage.jsp"><button type="button">Return Home</button></a>
	<% } else{ %>
		<a href="nonregistered_access.jsp"><button type="button">Return Home</button></a>
	<% } %>
</body>
</html>