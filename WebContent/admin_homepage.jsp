<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="hw6Quiz.*, java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager")); %>
<title>Admin</title>
</head>
<body>
<h1>Welcome <%= request.getParameter("email") %></h1>
</body>
</html>