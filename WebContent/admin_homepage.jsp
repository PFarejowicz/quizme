<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hw6Quiz.manager.*, java.text.*, java.util.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager")); %>
<% UserManager userManager = (UserManager) getServletContext().getAttribute("user manager"); %>
<% int userId = (Integer) session.getAttribute("user id"); %>
<title>Admin</title>
</head>
<body>
<form action="LogoutServlet" method="post">
	<input type="submit" value="Log Out" />
</form>
<h1 class="auth-center">Welcome <%= userManager.getNameByID(userId) %></h1>

<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
<div class="card">
<h5 class="auth-center">Announcements</h5>
<ul>
<% for(int i = 0; i < announcements.size(); i++){ %>
<li><%= announcements.get(i) %></li>
<% } %>
</ul>
<form action="AdminCreateAnnouncementServlet" method="post">
<p><input type="text" name="announcement" /></p>
<p><input type="submit" value="Add Announcement" /></p>
</form>
</div>

<% ArrayList<User> admins = adminManager.getAdmins(); %>
<% if(admins.size() > 1){ %>
<div class="card">
<h5 class="auth-center">Admins</h5>
<% for(int i = 0; i < admins.size(); i++){ %>
	<% if (admins.get(i).getId() != userId){ %>
		<p><%= admins.get(i).getName() %></p>
		<form action="AdminRemoveUserServlet" method="post">
		<input name="id" type="hidden" value="<%= admins.get(i).getId() %>"/>
		<input type="submit" value="Delete User" style="color:red"/>
		</form>
	<% } %>
<% } %>
</div>
<% } %>

<% ArrayList<User> nonAdmins = adminManager.getNonAdmins(); %>
<% if(nonAdmins.size() > 0){ %>
<div class="card">
<h5 class="auth-center">Users</h5>
<% for(int i = 0; i < nonAdmins.size(); i++){ %>
	<p><%= nonAdmins.get(i).getName() %></p>
	<form action="AdminPromotionServlet" method="post">
	<input name="id" type="hidden" value="<%= nonAdmins.get(i).getId() %>"/>
	<p><input type="submit" value="Promote to Admin" /></p>
	</form>
	<form action="AdminRemoveUserServlet" method="post">
	<input name="id" type="hidden" value="<%= nonAdmins.get(i).getId() %>"/>
	<p><input type="submit" value="Delete User" style="color:red"/></p>
	</form>
<% } %>
</div>
<% } %>

<% ArrayList<Quiz> quizzes = adminManager.getQuizzes(); %>
<% if(quizzes.size() > 0){ %>
<div class="card">
<h5 class="auth-center">Quizzes</h5>
<% for(int i = 0; i < quizzes.size(); i++){ %>
	<p><%= quizzes.get(i).getName() %></p>
	<form action="AdminClearQuizHistoryServlet" method="post">
	<p><input name="id" type="hidden" value="<%= quizzes.get(i).getQuizID() %>"/>
	<input type="submit" value="Clear Quiz History" /></p>
	</form>
	<form action="AdminRemoveQuizServlet" method="post">
	<p><input name="id" type="hidden" value="<%= quizzes.get(i).getQuizID() %>"/>
	<input name="admin_edit" type="hidden" value="true"/>
	<input type="submit" value="Delete Quiz" style="color:red"/></p>
	</form>
<% } %>
</div>
<% } %>

<% ArrayList<Quiz> reportedQuizzes = adminManager.getReportedQuizzes(); %>
<% if(reportedQuizzes.size() > 0){ %>
<div class="card">
<h5 class="auth-center">Reported Quizzes</h5>
<% for(int i = 0; i < reportedQuizzes.size(); i++){ %>
	<%= reportedQuizzes.get(i).getName() %>
	<form action="UnreportQuizServlet" method="post">
		<input type="hidden" name="quiz_id" value="<%= reportedQuizzes.get(i).getQuizID() %>"/>
		<input type="submit" value="Clear Report"/>
	</form>
		<form action="AdminRemoveQuizServlet" method="post">
		<input name="id" type="hidden" value="<%= reportedQuizzes.get(i).getQuizID() %>"/>
		<input name="admin_edit" type="hidden" value="true"/>
		<input type="submit" value="Delete Quiz" style="color:red"/>
	</form>
<% } %>
</div>
<% } %>

<% ArrayList<User> users = adminManager.getUsers(); %>
<div class="card">
<h5 class="auth-center">Site Statistics</h5>
<p>Number of users: <%= users.size() %></p>
<p>Number of quizzes taken: <%= adminManager.getNumberOfQuizzes() %></p>
</div>

</body>
</html>