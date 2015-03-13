<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, hw6Quiz.manager.*, hw6Quiz.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit Quiz</title>
	</head>
	<body>
		<%
			QuizManager quizManager = (QuizManager) application.getAttribute("quiz manager");
			AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager"));
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			adminManager.clearHistoryForQuiz(quiz_id);
			int user_id = (Integer) session.getAttribute("user id");
			Quiz quiz = quizManager.getQuizByID(quiz_id);
		%>
		<div style="text-align:center">
			<div style="display:inline-block">
				<h1 class="auth-center">Edit Quiz</h1><br>
				<form class="auth-center" action="QuizCreationServlet" method="post">
					<p class="auth-center">Name: <input class="field-center" type="text" name="name" value="<%=quiz.getName()%>"/></p><br><br>
					<p>Description: <br><textarea rows="4" cols="50" name="description"><%=quiz.getDescription()%></textarea></p><br>
					<p>Category: 
						<select name="category">
							<option value="animals" <% if (quiz.getCategory().equals("animals")) out.println("selected"); %>>Animals</option>
							<option value="entertainment" <% if (quiz.getCategory().equals("entertainment")) out.println("selected"); %>>Entertainment</option>
							<option value="fun" <% if (quiz.getCategory().equals("fun")) out.println("selected"); %>>Fun</option>
							<option value="gaming" <% if (quiz.getCategory().equals("gaming")) out.println("selected"); %>>Gaming</option>
							<option value="geography" <% if (quiz.getCategory().equals("geography")) out.println("selected"); %>>Geography</option>
							<option value="history" <% if (quiz.getCategory().equals("history")) out.println("selected"); %>>History</option>
							<option value="hobbies" <% if (quiz.getCategory().equals("hobbies")) out.println("selected"); %>>Hobbies</option>
							<option value="literature" <% if (quiz.getCategory().equals("literature")) out.println("selected"); %>>Literature</option>
							<option value="language" <% if (quiz.getCategory().equals("language")) out.println("selected"); %>>Language</option>
							<option value="miscellaneous" <% if (quiz.getCategory().equals("miscellaneous")) out.println("selected"); %>>Miscellaneous</option>
							<option value="movies" <% if (quiz.getCategory().equals("movies")) out.println("selected"); %>>Movies</option>
							<option value="music" <% if (quiz.getCategory().equals("music")) out.println("selected"); %>>Music</option>
							<option value="religion" <% if (quiz.getCategory().equals("religion")) out.println("selected"); %>>Religion</option>
							<option value="science" <% if (quiz.getCategory().equals("science")) out.println("selected"); %>>Science</option>
							<option value="sports" <% if (quiz.getCategory().equals("sports")) out.println("selected"); %>>Sports</option>
							<option value="television" <% if (quiz.getCategory().equals("television")) out.println("selected"); %>>Television</option>
							<option value="world" <% if (quiz.getCategory().equals("world")) out.println("selected"); %>>World</option>
						</select>
					</p><br>
					<p>Tags (separate with comma): <br><textarea rows="4" cols="50" name="tags" ><%=quiz.getTags()%></textarea></p><br>
					<p>Random order:
					<input type="radio" name="random_order" value="yes" <% if (quiz.isRandomOrder()) out.println("checked"); %>/> Yes
					<input type="radio" name="random_order" value="no" <% if (!quiz.isRandomOrder()) out.println("checked"); %>/> No 
					</p><br>
					<p>Multiple pages:
					<input type="radio" name="multiple_pages" value="yes" <% if (quiz.isMultiplePages()) out.println("checked"); %>/> Yes
					<input type="radio" name="multiple_pages" value="no" <% if (!quiz.isMultiplePages()) out.println("checked"); %>/> No 
					</p><br>
					<p>Immediate correction:
					<input type="radio" name="immediate_correction" value="yes" <% if (quiz.isImmediateCorrection()) out.println("checked"); %>/> Yes
					<input type="radio" name="immediate_correction" value="no" <% if (!quiz.isImmediateCorrection()) out.println("checked"); %>/> No 
					</p><br>
					<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
					<input type="hidden" name="edit_mode" value="true"/>
					<input  class="auth-center" type="submit" value="Continue Editing" />
				</form>
			</div>
		</div>
	</body>
</html>