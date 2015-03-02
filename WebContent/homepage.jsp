<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	String email = request.getParameter("email");
	/* int userId = (Integer) getServletContext().getAttribute("user id"); */
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QuizMe</title>
</head>
<body>

<h1>Welcome, <%=email%>!</h1>

<p>Messages</p>

<p>History</p>

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


<p>Achievements</p>

<p>Quiz</p>

<p><a href="create_quiz.jsp">Create a Quiz</a></p>


</body>
</html>


<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome <%= request.getParameter("name")%></title>
</head>
<body>
	<h1>Welcome <%= request.getParameter("name")%></h1>
</body>
</html>



<% 
ProductCatalog catalog = (ProductCatalog) getServletContext().getAttribute("catalog");
DBConnection db = (DBConnection) getServletContext().getAttribute("db");
ArrayList<Product> items = catalog.makeCatalog(db);
String id = request.getParameter("id");
Product item = catalog.getProductGivenId(id);
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=item.getName()%></title>
</head>
<body>
	<h1><%=item.getName()%></h1>
	<img src="<%=item.getImage() %>"/>
	<p>$<%=item.getPrice().toString() %>
	<form action="ShoppingCartServlet" method="post">
	<input name="id" type="hidden" value="<%=id%>">
	<input type="submit" value="Add to Cart">
	</form>
	</p>
</body>
</html> --%>