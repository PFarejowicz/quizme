<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Multiple Choice with Multiple Answers</title>
	</head>
	<body>
		<h1>Create Multiple Choice with Multiple Answers</h1>
		<form action="QuestionCreationServlet" method="post">
			<p>Include the actual answer(s) in answer choices (the capitalizations should match). Separate the answer choices with commas (do not include , in the actual answer). Do not have duplicate answers.</p>
			<p>Question: <br>
			<textarea rows="4" cols="50" name="prompt" ></textarea></p>
			<p>Answer Choices: <br>
			<textarea rows="4" cols="50" name="choices" ></textarea></p>
			<p>Answer(s): <br>
			<textarea rows="4" cols="50" name="answer" ></textarea></p>

			<input type="hidden" name="ques_type" value="multiple_choice_multiple_answers"/>
			<input type="hidden" name="quiz_id" value="<%=request.getParameter("quiz_id")%>"/>
			<input type="hidden" name="points" value="<%=request.getParameter("points")%>"/>
			<input type="hidden" name="edit_mode" value="<%=request.getParameter("edit_mode")%>"/>
			<input type="submit" name="previous" value="Previous"/>
			<input type="submit" name="next" value="Next"/>
			<input type="submit" name="finish" value="Finish" />
		</form>
	</body>
</html>