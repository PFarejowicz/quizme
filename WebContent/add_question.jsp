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
		<title>Add Question</title>
	</head>
	<body>
		<h1>Select Question Type</h1>
		<form action="SelectQuestionTypeServlet" method="post">
			<p>Question Type: 
			<select name="ques_type">
				<option value="text_response">Question-Response Question</option>
				<option value="fill_blank">Fill in the Blank Question</option>
				<option value="multiple_choice">Multiple Choice Question</option>
				<option value="picture">Picture-Response Question</option>
				<option value="multianswer">Multiple-Answer Question</option>
				<option value="multiple_choice_multiple_answers">Multiple Choice with Multiple Answers</option>
			</select>
			<input type="hidden" name="quiz_id" value="<%=request.getParameter("quiz_id")%>"/>
			<input type="hidden" name="points" value="<%=request.getParameter("points")%>"/>
			<input type="hidden" name="edit_mode" value="<%=request.getParameter("edit_mode")%>"/>
			<p><input type="submit" value="Next" /></p>
		</form>
	</body>
</html>