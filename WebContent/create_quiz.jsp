<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<body>
<h1>Create Quiz</h1>
<form action="AccountCreationServlet" method="post">

<p>Name: <input type="text" name="name" />
<p>Description: <br><textarea rows="4" cols="50" name="description" ></textarea>
<p>Random order: <br><input type="radio" name="random_order" value="Yes" />
<input type="radio" name="random_order" value="No" />
<p>Multiple pages: <br><input type="radio" name="multiple_pages" value="Yes" />
<input type="radio" name="multiple_pages" value="No" />
<p>Immediate correction: <br><input type="radio" name="immediate_correction" value="Yes" />
<input type="radio" name="immediate_correction" value="No" />

<input  type="submit" value="Create" /></p>
</form>
</body>
</html>