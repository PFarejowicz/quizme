<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account Already in Use</title>
</head>
<body>
<h1>The email <%= request.getParameter("email") %> is already in use</h1>
<p>Please enter another email and password.</p>
<a href="create_account.html">Go back</a>
</body>
</html>