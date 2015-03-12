<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			<%
			QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
			QuestionManager questionManager = (QuestionManager) application.getAttribute("question manager");
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			Quiz quiz = quizManager.getQuizByID(quiz_id);
			out.println("Edit Quiz Questions");
			%>
		</title>
	</head>
	<body>
		<h1>Questions belonging to <%=quiz.getName() %></h1>
		<p>Select a question to be edited:</p>
		<table style="width:100%">
			<tr>
				<th>Question Number</th>
				<th>Question Type</th>
				<th>Question Prompt</th>
				<th>Question Edit</th>
			</tr>
			<% 
			ArrayList<Integer> questions = questionManager.getQuestionIDs(quiz_id);
			int question_number = 1;
			for (int question_id : questions) {
				String type = questionManager.getTypeByID(question_id);
				out.println("<tr>");
				out.println("<td>"+question_number+"</td>");
				if (type.equals("QuestionResponse")) {
					QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
					out.println("<td>Question Response</td>");
					out.println("<td>"+question.getQuestionText()+"</td>");
					out.println("<td><a href=\"edit_question_response_question.jsp?question_id="+question_id+"\"><button type=\"button\">Edit Question</button></a></td>");
				} else if (type.equals("FillInTheBlank")) {
					FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
					out.println("<td>Fill In The Blank</td>");
					out.println("<td>"+question.getQuestionText()+"</td>");
					out.println("<td><a href=\"edit_fill_blank_question.jsp?question_id="+question_id+"\"><button type=\"button\">Edit Question</button></a></td>");
				} else if (type.equals("MultipleChoice")) {
					MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
					out.println("<td>Multiple Choice</td>");
					out.println("<td>"+question.getQuestionText()+"</td>");
					out.println("<td><a href=\"edit_multiple_choice_question.jsp?question_id="+question_id+"\"><button type=\"button\">Edit Question</button></a></td>");
				} else if (type.equals("PictureResponse")) {
					PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
					out.println("<td>Picture Response</td>");
					out.println("<td>"+question.getQuestionText()+"</td>");
					out.println("<td><a href=\"edit_picture_question.jsp?question_id="+question_id+"\"><button type=\"button\">Edit Question</button></a></td>");
				}
				out.println("</tr>");
				question_number++;
			}
			%>
		</table>
		<a href="quiz_summary.jsp?quiz_id=<%=quiz_id %>"><button type="button">Finish Editing</button></a>
	</body>
</html>