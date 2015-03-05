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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=friendName%>'s Profile</title>
</head>
<body>

<h1><%=friendName%></h1>

<p><%=friendName%>'s Quizzes</p>

<p><%=friendName%>'s Achievements</p>

<p><%=friendName%>'s Quiz History</p>

	<%
	if (isFriend == 2) {%>  
		<p>Friend Request Sent</p>
	<%}%>
	<%
	if (isFriend == 3) {%>  
		<p>Respond to Friend Request</p>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="submit" value="Accept" name="decision" />
		</form>
		<form action="FriendServlet" method="post">
		<input type="hidden" value=<%=friendEmail%> name="friendEmail" />
		<input type="submit" value="Reject" name="decision" />
		</form>
	<%}%>
	<%
	if (isFriend == 4) {%>  
		<p>Add Friend</p>
		<form action="FriendRequestServlet" method="post">
		<p><input type="hidden" value=<%=friendEmail%> name="friendEmail" />
	    <input type="submit" value="Send Friend Request" /></p>
	    </form>
	<%}%>
		
<p><%=friendName%>'s Friends</p>
	<ul>
	<% 
	ArrayList<Integer> friendsList = friendsManager.getFriends(friendId);
	%>
	<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
		<li><%=userManager.getNameByID(friendsList.get(i))%></li>
	<%}%>
	</ul>

</body>
</html>