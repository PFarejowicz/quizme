<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<% UserManager userManager = (UserManager) getServletContext().getAttribute("user manager"); %>
<% int userId = Integer.parseInt(request.getParameter("id")); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quiz History</title>
</head>
<body>
<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(userId); %>
<h1><%=userManager.getNameByID(userId)%>'s Quiz History</h1>
<% for(int i = history.size() - 1; i >= 0; i--){ %>
	<p>Quiz Name: <%= history.get(i).getName() %></p>
	<p>Score: <%=String.format("%.2f", (float)history.get(i).getScore()/(float)history.get(i).getTotal()*100) %>%</p><br/>
<% } %>
</body>
</html>