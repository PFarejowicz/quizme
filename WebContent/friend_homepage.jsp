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
	boolean isFriend = friendsManager.checkFriend(userId, friendId);
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
	if (!isFriend) {%>  
		<p>Add Friend</p>
		<form action="FriendRequestServlet" method="post">
		<p><input type="hidden" value=<%=friendEmail%> name="friendEmail" />
	    <input type="submit" value="Add" /></p>
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
	
<!-- <p>Send Messages</p>
	<form action="MessageServlet" method="post">
	<p>Send to: <input type="text" name="receiver" />
	<p>Message: <input type="text" name="new message" />
	<input type="submit" value="Send" /></p>
	</form> -->


</body>
</html>