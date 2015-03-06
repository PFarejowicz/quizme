<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
	String friendEmail = request.getParameter("friendEmail");
	int friendId = userManager.getIDByEmail(friendEmail);
	int userId = (Integer) session.getAttribute("user id");
	String friendName = userManager.getNameByID(friendId);
	int isFriend = friendsManager.checkFriendStatus(userId, friendId);
%>

<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=friendName%>'s Profile</title>
</head>
<body>

<a href="homepage.jsp"><button type="button">Back to My Profile</button></a>

<h1><%=friendName%></h1>

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

<p><%=friendName%>'s Quizzes</p>

<p><%=friendName%>'s Achievements</p>

<p><%=friendName%>'s Quiz History</p>
		
<p><%=friendName%>'s Friends</p>
	<ul>
	<% 
	ArrayList<Integer> friendsList = friendsManager.getFriends(friendId);
	%>
	<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
		<%
		if (friendsList.get(i) != userId) {%>
				<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getEmailByID(friendsList.get(i))%></a></li>
		<%}%>
	<%}%>
	</ul>
	
	<%
	if (isFriend == 1) {%> 
		<p> Unfriend <%=friendName%></p>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="submit" value="Unfriend" name="decision"/>
		</form>
	<%}%>
	
</body>
</html>