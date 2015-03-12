<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>
		<%
		final int num_scores = 3;
		QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
		UserManager userManager = (UserManager) application.getAttribute("user manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = (Integer) session.getAttribute("user id");
		Quiz quiz = quizManager.getQuizByID(quiz_id);
		out.println(quiz.getName());
		%>
	</title>
</head>
<body>
	<h1>Welcome to a QuizMe Quiz</h1>
	<p>
		Quiz Name: <%=quiz.getName() %><br>
		Quiz Description: <%=quiz.getDescription() %> <br>
		Quiz Author: <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(quiz.getAuthorID())%>"><%=userManager.getNameByID(quiz.getAuthorID()) %></a>
		Quiz Rating: <%=String.format("%.2f", (float)quizManager.calculateRating(quiz_id)) %>
	</p>
	<p>
		Questions Presented in Random Order? <%= quiz.isRandomOrder() ? "Yes" : "No" %><br>
		Questions Presented on Multiple Pages? <%= quiz.isMultiplePages() ? "Yes" : "No" %><br>
		Immediate Corrections Provided for Questions? <%= quiz.isImmediateCorrection() ? "Yes" : "No" %>
	</p>
	
	<h2>Your Past Performance</h2>
	<p><%
		ArrayList<QuizHistory> pastPerformance = quizManager.getQuizHistory(quiz_id, user_id, "past performance");
		int size = pastPerformance.size();
		for (int i = 0; i < size; i++) {
	        out.println("Date: " + pastPerformance.get(i).getTimeStamp() + " Score: " + pastPerformance.get(i).getScore());
	        out.println("<br>");
		}
	%></p>
	
	<h2>All Time High Scores</h2>
	<p><%
		ArrayList<QuizHistory> allTimeHigh = quizManager.getQuizHistory(quiz_id, user_id, "all time high");
		size = allTimeHigh.size();
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
		out.println("Average: " + quizManager.convertToPercStr(quizManager.avgQuizScore(quiz_id), quiz.getPoints()));
		out.println("<br>");
		out.println("Range: " + quizManager.quizRange(quiz_id));
		out.println("<br>");
		out.println("Times taken: " + quizManager.numTimesTaken(quiz_id));
		out.println("<br>");
	%></p>
	
	
	<a href="homepage.jsp"><button type="button">Return Home</button></a>
	<form action="QuizStartServlet" method="post" style="display: inline">
		<p>Mode: <br>
		<input type="radio" name="mode" value="regular" /> Regular <br>
		<input type="radio" name="mode" value="practice" /> Practice 
		</p>
		<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
		<input type="hidden" name="random_order" value="<%=quiz.isRandomOrder()%>"/>
		<input type="hidden" name="multiple_pages" value="<%=quiz.isMultiplePages()%>"/>
		<input type="hidden" name="immediate_correction" value="<%=quiz.isImmediateCorrection()%>"/>
		<input  type="submit" value="Take Quiz" />
	</form>
</body>
</html>