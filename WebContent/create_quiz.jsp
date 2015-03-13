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
		<title>Create Quiz</title>
	</head>
	<body>
		<h1>Create Quiz</h1>
		<form action="QuizCreationServlet" method="post">
			<p>Name: <input type="text" name="name" /></p>
			<p>Description: <br><textarea rows="4" cols="50" name="description" ></textarea></p>
			<p>Category: 
				<select name="category">
					<option value="animals">Animals</option>
					<option value="entertainment">Entertainment</option>
					<option value="fun">Fun</option>
					<option value="gaming">Gaming</option>
					<option value="geography">Geography</option>
					<option value="history">History</option>
					<option value="hobbies">Hobbies</option>
					<option value="literature">Literature</option>
					<option value="language">Language</option>
					<option value="miscellaneous">Miscellaneous</option>
					<option value="movies">Movies</option>
					<option value="music">Music</option>
					<option value="religion">Religion</option>
					<option value="science">Science</option>
					<option value="sports">Sports</option>
					<option value="television">Television</option>
					<option value="world">World</option>
				</select>
			</p>
			<p>Tags (separate with comma): <br><textarea rows="4" cols="50" name="tags" ></textarea></p>
			<p>Random order: <br>
				<input type="radio" name="random_order" value="yes" /> Yes <br>
				<input type="radio" name="random_order" value="no" /> No 
			</p>
			<p>Multiple pages: <br>
				<input type="radio" name="multiple_pages" value="yes" /> Yes <br>
				<input type="radio" name="multiple_pages" value="no" /> No 
			</p>
			<p>Immediate correction: <br>
				<input type="radio" name="immediate_correction" value="yes" /> Yes <br>
				<input type="radio" name="immediate_correction" value="no" /> No 
			</p>
			<input type="hidden" name="edit_mode" value="false"/>
			<input type="submit" value="Create" />
		</form>
	</body>
</html>