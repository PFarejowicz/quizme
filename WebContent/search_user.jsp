<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% 
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	String info = (String) session.getAttribute("info");
	ArrayList<Integer> userInfo = userManager.findUsers(info);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Search</title>
</head>
<body>

<h1>Search Result for <%=info%>:</h1>

	<ul>
	<%for (int i = 0 ; i < userInfo.size() ; i++) { %>
		<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(userInfo.get(i))%>"><%=userManager.getNameByID(userInfo.get(i))%></a></li>
		
	<%}%>
	</ul>

</body>
</html>