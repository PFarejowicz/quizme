<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = 0;
		if((Integer) request.getSession().getAttribute("user id") != null) {
			user_id = (Integer) request.getSession().getAttribute("user id");
		}
		String quiz_name = quizManager.getQuizNameByID(quiz_id);
		%>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>QuizMe Quiz Reviews</title>
	</head>
	<body>
		<h1 class="auth-center"><%=quiz_name%> Reviews</h1>
		<br/>
		<%
		ArrayList<QuizHistory> quiz_history = quizManager.getQuizHistory(quiz_id, user_id, "reviews");
		int size = quiz_history.size();
		for (int i = 0; i < size; i++) {
			out.println(quiz_history.get(i).getUserId());
			out.println(quiz_history.get(i).getReview());
		}
		%>
		<p id="quiz_search_return_home"><a href="homepage.jsp"><button type="button">Return Home</button></a></p>
	</body>
</html>