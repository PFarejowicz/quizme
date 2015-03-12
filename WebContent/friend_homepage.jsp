<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
	QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
	String friendEmail = request.getParameter("friendEmail");
	int friendId = userManager.getIDByEmail(friendEmail);
	int userId = 0;
	if((Integer) session.getAttribute("user id") != null){
		userId = (Integer) session.getAttribute("user id");
	}
	String email = (String) session.getAttribute("email");
	String friendName = userManager.getNameByID(friendId);
	int isFriend = friendsManager.checkFriendStatus(userId, friendId);
%>

<html>
<head>
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><%=friendName%>'s Profile</title>
</head>
<body>

<% if(email != null){ %>
<a href="homepage.jsp"><button type="button">Back to My Profile</button></a>
<% } else{ %>
<a href="nonregistered_access.jsp"><button type="button">Back Home</button></a>
<% } %>

<h1><%=friendName%></h1>

<% if(email != null){ %>
<%
	if (isFriend == 2) {%>  
		<p>Friend Request Sent</p>
	<%}%>
	<%
	if (isFriend == 3) {%>  
		<p>Respond to Friend Request</p>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="hidden" value="Accept From Friend Page" name="decision" />
		<input type="submit" value="Accept Friend Request" />
		</form>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="hidden" value="Reject From Friend Page" name="decision" />
		<input type="submit" value="Reject Friend Request" />
		</form>
	<%}%>
	<%
	if (isFriend == 4) {%>  
		<p>Add Friend</p>
		<form action="FriendRequestServlet" method="post">
		<p><input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="hidden" value="Request from Friend Page" name="requestSent" />
	    <input type="submit" value="Send Friend Request" /></p>
	    </form>
	<%}%>
<% } %>

<p><%=friendName%>'s Quizzes</p>
	<% ArrayList<Quiz> friendQuizzes = userManager.getAuthoredQuizzes(friendId); %>
		<% if(friendQuizzes.size() > 0){ %>
			<h5><%=friendName%>'s Most Recent Created Quizzes:</h5><br/>
			<% for(int i = friendQuizzes.size() - 1; i >= 0 && i >= friendQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= friendQuizzes.get(i).getQuizID() %>"><%= friendQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= friendQuizzes.get(i).getDescription() %></p>
			<% } %>
		<% } %>

<p><%=friendName%>'s Achievements</p>
		<ul>
			<% ArrayList<String> achievements = quizManager.getAchievements(friendId); 
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
		
<p><%=friendName%>'s Quiz History</p>
		<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(friendId); %>
		<% if(history.size() > 0){ %>
			<h5><%=friendName%>'s Most Recent Taken Quizzes:</h5><br/>
			<% for(int i = history.size() - 1; i >= 0 && i >= history.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= history.get(i).getQuizId() %>"><%= history.get(i).getName() %></a></p>
				<p>Score: <%=quizManager.convertToPercStr(history.get(i).getScore(), history.get(i).getTotal()) %></p><br/>
			<% } %>
		<% } %>
		<a href="user_quiz_history.jsp?id=<%= friendId %>"><button type="button">Show Full History</button></a>
		
<p><%=friendName%>'s Friends</p>
	<ul>
	<% 
	ArrayList<Integer> friendsList = friendsManager.getFriends(friendId);
	%>
	<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
		<%
		if (friendsList.get(i) != userId) {%>
				<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%></a></li>
		<%}%>
	<%}%>
	</ul>
	
	<% if(email != null){ %>
	<%
	if (isFriend == 1) {%> 
		<p> Unfriend <%=friendName%></p>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="submit" value="Unfriend" name="decision"/>
		</form>
	<%}%>
	<% } %>
	
</body>
</html>