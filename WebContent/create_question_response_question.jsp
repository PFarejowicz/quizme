<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Question Response Question</title>
</head>
<body>
	<h1>Create Question Response Question</h1>
	<form action="QuestionCreationServlet" method="post">
		<p>Question: <br>
		<textarea rows="4" cols="50" name="prompt" ></textarea></p>
		<p>Answer: <br>
		<textarea rows="4" cols="50" name="answer" ></textarea></p>
		<input type="hidden" name="ques_type" value="question_response"/>
		<%
			String quiz_id = (String) request.getAttribute("quiz_id");
		%>
		<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
		<input type="hidden" name="points" value="<%=request.getParameter("points")%>"/>
		<input type="submit" name="next" value="Next"/>
		<input type="submit" name="previous" value="Previous"/>
		<input type="submit" name="finish" value="Finish" />
	</form>
</body>
</html>