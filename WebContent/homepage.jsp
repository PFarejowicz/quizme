<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	String email = request.getParameter("email");
	int userId = (Integer) session.getAttribute("user id");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QuizMe</title>
</head>
<body>

<h1>Welcome, <%=userManager.getNameByID(userId)%>!</h1>

<p>Quiz</p>

<p>Achievements</p>

<p><a href="create_quiz.jsp">Create a Quiz</a></p>

<p>History</p>

<p>Friends</p>
	<form action="FriendServlet" method="post">
	<p>Add: <input type="text" name="friend" />
	<input type="submit" value="Add" /></p>
	</form>
	
<p>Your Friends</p>
<%-- 	<ul>
	<% 
	ArrayList<String> messages = messageManager.getMessage(email);
	%>
	<%for (int i = messages.size() - 1 ; i >= 0 ; i-=2) { %>
		<li><%=messages.get(i)%>: <%=messages.get(i-1)%></li>
	<%}%>
	</ul> --%>
	
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
		<li><%=messages.get(i)%>: <%=messages.get(i-1)%></li>
	<%}%>
	</ul>

</body>
</html>