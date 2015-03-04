<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hw6Quiz.manager.*, java.text.*, java.util.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager")); %>
<% UserManager userManager = (UserManager) getServletContext().getAttribute("user manager"); %>
<% int userId = (Integer) session.getAttribute("user id"); %>
<title>Admin</title>
</head>
<body>
<h1>Welcome <%= userManager.getNameByID(userId) %></h1>
<h3>Announcements</h3>
<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
<ul>
<% for(int i = 0; i < announcements.size(); i++){ %>
<li><%= announcements.get(i) %></li>
<% } %>
</ul>
<form action="AdminCreateAnnouncementServlet" method="post">
<p><input type="text" name="announcement" /></p>
<input type="submit" value="Add Announcement" />
</form>
<% ArrayList<User> users = adminManager.getUsers(); %>
<h3>Admins</h3>
<% for(int i = 0; i < users.size(); i++){ %>
	<% if (users.get(i).getAdmin()){ %>
		<p><%= users.get(i).getName() %></p>
		<form action="AdminRemoveUserServlet" method="post">
		<input name="id" type="hidden" value="<%= users.get(i).getId() %>"/>
		<input type="submit" value="Delete User" />
		</form>
	<% } %>
<% } %>
<h3>Users</h3>
<% for(int i = 0; i < users.size(); i++){ %>
	<% if (!users.get(i).getAdmin()){ %>
		<p><%= users.get(i).getName() %></p>
		<form action="AdminPromotionServlet" method="post">
		<input name="id" type="hidden" value="<%= users.get(i).getId() %>"/>
		<input type="submit" value="Promote to Admin" />
		</form>
		<form action="AdminRemoveUserServlet" method="post">
		<input name="id" type="hidden" value="<%= users.get(i).getId() %>"/>
		<input type="submit" value="Delete User" />
		</form>
	<% } %>
<% } %>
<% ArrayList<Quiz> quizzes = adminManager.getQuizzes(); %>
<h3>Quizzes</h3>
<% for(int i = 0; i < quizzes.size(); i++){ %>
	<%= quizzes.get(i).getName() %>
	<form action="AdminClearQuizHistoryServlet" method="post">
	<p><input name="id" type="hidden" value="<%= quizzes.get(i).getQuizID() %>"/>
	<input type="submit" value="Clear Quiz History" /></p>
	</form>
	<form action="AdminRemoveQuizServlet" method="post">
	<p><input name="id" type="hidden" value="<%= quizzes.get(i).getQuizID() %>"/>
	<input type="submit" value="Delete Quiz" /></p>
	</form>
<% } %>
<h3>Site Statistics</h3>
<p>Number of users: <%= users.size() %></p>
<p>Number of quizzes taken: <%= adminManager.getNumberOfQuizzes() %></p>
</body>
</html>