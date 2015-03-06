<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<body>
<h1>Create Quiz</h1>
<form action="QuizCreationServlet" method="post">

<p>Name: <input type="text" name="name" /></p>
<p>Description: <br><textarea rows="4" cols="50" name="description" ></textarea></p>
<p>Random order: <br>
<input type="radio" name="random_order" value="Yes" /> Yes <br>
<input type="radio" name="random_order" value="No" /> No 
</p>
<p>Multiple pages: <br>
<input type="radio" name="multiple_pages" value="Yes" /> Yes <br>
<input type="radio" name="multiple_pages" value="No" /> No 
</p>
<p>Immediate correction: <br>
<input type="radio" name="immediate_correction" value="Yes" /> Yes <br>
<input type="radio" name="immediate_correction" value="No" /> No 
</p>

<input  type="submit" value="Create" />
</form>
</body>
</html>