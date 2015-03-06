<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
	QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
	String email = (String) session.getAttribute("email");
	int userId = (Integer) session.getAttribute("user id");
	String messageStatus = (String) session.getAttribute("message status");
%>

<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QuizMe</title>
</head>
<body>

<a href="index.html"><button type="button">Log Out</button></a>


<h1>Welcome, <%=userManager.getNameByID(userId)%>!</h1>

<p><a href="create_quiz.jsp">Create a Quiz</a></p>
<p><a href="quiz_archive">Go to Quiz Archive</a></p>

<h3>Your Quizzes</h3>

<h3><a href="create_quiz.jsp">Create a Quiz</a></h3>


<h3>Your Quizzes</h3>

<h3>Achievements</h3>
<ul>
<% ArrayList<String> achievements = quizManager.getAchievements(userId); 
String check = "I am the Greatest";
%>
<% 
for (int i = 0 ; i < achievements.size() ; i++) { %>
	<% String description = achievements.get(i);
	String quizId = ""; %>
	<% if (description.contains(check)) { %>
		<% quizId = description.substring(check.length());
		description = check; %>
		<li><%=description%>: <%= quizManager.getQuizByID(Integer.parseInt(quizId)).getName() %></li>
	<%} else {%>
		<li><%= description %></li>
	<%}%>
<%}%>
</ul>

<h3><a href="create_quiz.jsp">Create a Quiz</a></h3>

<h3>Quiz History</h3>
<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(userId); %>
<% if(history.size() > 0){ %>
	<h5>Your Most Recent Quizzes:</h5><br/>
	<% for(int i = history.size() - 1; i >= 0 && i >= history.size() - 3; i--){ %>
		<p>Quiz Name:<%= history.get(i).getQuizId() %></p>
		<p>Score: <%= history.get(i).getScore() %></p><br/>
	<% } %>
<% } %>
<form action="UserQuizHistoryServlet" method="post">
	<input name="id" type="hidden" value="<%= userId %>"/>
	<input type="submit" value="Show Full History" />
	</form>

<h3>Your Friends</h3>
	<ul>
	<% 
	ArrayList<Integer> friendsList = friendsManager.getFriends(userId);
	%> 
	<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
		<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getEmailByID(friendsList.get(i))%></a></li>
		
	<%}%>
	</ul>
	

<h3>Pending Friend Requests</h3>

<h3>Challenge Friends</h3>
	
	
<h3>Pending Friend Requests</h3>
	<ul>
	<% 
	ArrayList<Integer> requestList = friendsManager.showFriendRequests(userId);
	%>
	<%
	for (int i = 0 ; i < requestList.size() ; i++) { %>
		<% 
		int otherUserId = requestList.get(i); 
		String otherUserEmail = userManager.getEmailByID(otherUserId);
		%>
			<li><a href="friend_homepage.jsp?friendEmail=<%=otherUserEmail%>"><%=userManager.getNameByID(requestList.get(i))%></a>
			<form action="FriendServlet" method="post">
			<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
		    <input type="hidden" value="Accept From Home Page" name="decision" />
		    <input type="submit" value="Accept Friend Request" />
		    </form>
		    <form action="FriendServlet" method="post">
			<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
			<input type="hidden" value="Reject From Home Page" name="decision" />
		    <input type="submit" value="Reject Friend Request" />
		    </form>
		</li>
	<%}%>
	</ul>

<h3>Search Users</h3>
	<form action="UserSearchServlet" method="post">
	<p><input type="text" name="info" />
	<input type="submit" value="Search" /></p>
	</form>

<h3>Send Messages</h3>
	
	<form action="MessageServlet" method="post">
	<p>Send to: <input type="text" name="receiver" /></p>
	<p>Message: <input type="text" name="new message" />
	<input type="submit" value="Send" /></p>
	</form>
	<% if (messageStatus != null) { %>
		<% if (messageStatus.equals("sent")) { %>
			<p>Message Sent!</p>
		<% } %>
		<% if (messageStatus.equals("failed")) { %>
			<p>Failed to send message: Invalid user email</p>
		<% } %>
	<% } %>

<h3>Your Messages</h3>
	<ul>
	<% 
	ArrayList<String> messages = messageManager.getMessage(email);
	%>
	<%for (int i = messages.size() - 1 ; i >= 0 ; i-=2) { %>
		<li><a href="friend_homepage.jsp?friendEmail=<%=messages.get(i)%>"><%=messages.get(i)%></a>: <%=messages.get(i-1)%></li>
	<%}%>
	</ul>

</body>
</html>