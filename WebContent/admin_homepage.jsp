<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hw6Quiz.manager.*, java.text.*, java.util.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager")); %>
<title>Admin</title>
</head>
<body>
<h1>Welcome <%= request.getParameter("email") %></h1>
<h3>Announcements</h3>
<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
<ul>
<% for(int i = 0; i < announcements.size(); i++){ %>
<li><%= announcements.get(i) %></li>
<% } %>
</ul>
<% ArrayList<User> users = adminManager.getUsers(); %>
<h3>Admins</h3>
<ul>
<% for(int i = 0; i < users.size(); i++){ %>
	<% if (users.get(i).getAdmin()){ %>
		<li><%= users.get(i).getName() %></li>
	<% } %>
<% } %>
</ul>
<h3>Users</h3>
<ul>
<% for(int i = 0; i < users.size(); i++){ %>
	<% if (!users.get(i).getAdmin()){ %>
		<li><%= users.get(i).getName() %></li>
	<% } %>
<% } %>
</ul>
<% ArrayList<String> quizzes = adminManager.getQuizzes(); %>
<h3>Quizzes</h3>
<ul>
<% for(int i = 0; i < quizzes.size(); i++){ %>
<li><%= quizzes.get(i) %></li>
<% } %>
</ul>
<h3>Site Statistics</h3>
<p>Number of users: <%= users.size() %></p>
<p>Number of quizzes taken: <%= adminManager.getNumberOfQuizzes() %></p>
</body>
</html>