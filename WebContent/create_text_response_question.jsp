<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Text Response Question</title>
</head>
<body>
<h1>Create Text Response Question</h1>
<form action="QuestionCreationServlet" method="post">
</form>
<p>Question: <br>
<textarea rows="4" cols="50" name="prompt" ></textarea>
<input type="hidden" name="ques_type" value="text_response"/>

<!-- TODO -->
<input type="submit" name="next" value="Next" />
<input type="submit" name="previous" value="Previous">
<input type="submit" name="finish" value="Finish" />
</p>
</body>
</html>