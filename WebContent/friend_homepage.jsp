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
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=userManager.getNameByID(friendId)%>'s Profile</title>
</head>
<body>

<h1><%=userManager.getNameByID(friendId)%></h1>


</body>
</html>