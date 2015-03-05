<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
	String email = (String) session.getAttribute("email");
	int userId = (Integer) session.getAttribute("user id");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QuizMe</title>
</head>
<body>

<h1>Welcome, <%=userManager.getNameByID(userId)%>!</h1>

<p>Your Quizzes</p>

<p>Achievements</p>

<p><a href="create_quiz.jsp">Create a Quiz</a></p>

<p>Quiz History</p>

<p>Your Friends</p>
	<ul>
	<% 
	ArrayList<Integer> friendsList = friendsManager.getFriends(userId);
	%>
	<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
		<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getEmailByID(friendsList.get(i))%></a></li>
		
	<%}%>
	</ul>
	
<p>Pending Friend Requests</p>
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

<p>Search Users</p>
	<form action="UserSearchServlet" method="post">
	<p><input type="text" name="info" />
	<input type="submit" value="Search" /></p>
	</form>

<p>Send Messages</p>
	<form action="MessageServlet" method="post">
	<p>Send to: <input type="text" name="receiver" />
	<p>Message: <input type="text" name="new message" />
	<input type="submit" value="Send" /></p>
	</form>

<p>Your Messages</p>
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