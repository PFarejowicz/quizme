<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, java.util.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	
	<link rel="stylesheet" href="jquery.rating.css">
	<script src="jquery.js"></script>
	<script src="jquery.rating.js"></script>
	<title>
		<%
		FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
		UserManager userManager = (UserManager) application.getAttribute("user manager");
		QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
		QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
		MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = (Integer) session.getAttribute("user id");
		Quiz quiz = quizManager.getQuizByID(quiz_id);
		out.println(quiz.getName() + " Results");
		int score = Integer.parseInt(request.getParameter("score"));
		int total = quizManager.getQuizPoints(quiz_id);
		String name = quiz.getName();
		%>
	</title>
</head>
<body>
	<h1><%=quiz.getName()%> Results</h1>
	<p>Score: <%=score%> / <%= total%> (<%=quizManager.convertToPercStr(score, total) %>)</p>
	<p>Time Taken: <%=request.getParameter("time_taken")%></p> 
	<form action="QuizReviewServlet" method="post">
		<p>Review:</p>
		<textarea name="review" rows="5" cols="50"></textarea>
		<p>Rating:</p>
		<input type="radio" name="rating" value="1" class="star">
        <input type="radio" name="rating" value="2" class="star">
        <input type="radio" name="rating" value="3" class="star">
        <input type="radio" name="rating" value="4" class="star">
        <input type="radio" name="rating" value="5" class="star">
        <br>
        <p>Your Past Performance</p>
		<ul><%
		ArrayList<QuizHistory> pastPerformance = quizManager.getQuizHistory(quiz_id, user_id, "past performance");
		int size = pastPerformance.size();
		for (int i = 0; i < size; i++) {
			out.println("<li>");
	        out.println("Date: " + pastPerformance.get(i).getTimeStamp() + " Score: " + pastPerformance.get(i).getScore());
		}
		%></ul>
		<p>Your Friends' Performances</p>
		<% 
			ArrayList<Integer> friendsList = friendsManager.getFriends(user_id);
		%> 
		<ul>
		<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
			<% if (quiz.getAuthorID() != friendsList.get(i)) { %>
				<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%> (<%=quizManager.convertToPercStr(userManager.getTopScore(user_id, quiz_id), total)%>%)</a>
				<form action="ChallengeServlet" method="post">
					<input type="hidden" value=<%=user_id%> name="sender_id" />
					<input type="hidden" value=<%=friendsList.get(i)%> name="receiver_id" />
					<input type="hidden" value=<%=quiz_id%> name="quiz_id" />
					<input type="hidden" value=<%=score%> name="score" />
					<input type="submit" value="Send Challenge" name="decision" />
				</form>
				</li>	
			<%}%>
		<%}%>
		</ul>
		
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>" />
        <input type="hidden" name="score" value="<%=score%>" />
        <input type="hidden" name="total" value="<%=total%>" />
        <input type="hidden" name="time_taken" value="<%=request.getParameter("time_taken")%>" />
        <input type="hidden" name="name" value="<%=name%>" />
        <input type="submit" name="finish" value="Finish" />
	</form>
</body>
</html>