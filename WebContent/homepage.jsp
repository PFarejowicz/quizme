<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, hw6Quiz.*, hw6Quiz.manager.*, hw6Quiz.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserManager userManager = (UserManager) getServletContext().getAttribute("user manager");
	AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager"));
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	MessageManager messageManager = (MessageManager) getServletContext().getAttribute("message manager");
	FriendsManager friendsManager = (FriendsManager) getServletContext().getAttribute("friends manager");
	QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
	String email = (String) session.getAttribute("email");
	int userId = (Integer) session.getAttribute("user id");
	String messageStatus = (String) session.getAttribute("message status");
%>

<html>
<head>
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>QuizMe</title>
</head>
	<body>
		<form action="LogoutServlet" method="post">
			<input type="submit" value="Log Out" />
		</form>
		
		<h1 class="auth-center">Welcome, <%=userManager.getNameByID(userId)%>!</h1>
		
		<p><a href="create_quiz.jsp">Create a Quiz</a></p>
		<p><a href="quiz_archive.jsp">Go to Quiz Archive</a></p>
		
		<h3 class="auth-center">Announcements</h3>
		<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
		<ul>
			<% for(int i = 0; i < announcements.size(); i++){ %>
			<li><%= announcements.get(i) %></li>
			<% } %>
		</ul>
		
		<h3 class="auth-center">Popular Quizzes</h3>
		<% ArrayList<Quiz> popularQuizzes = quizManager.getPopularQuizzes(); %>
		<% if(popularQuizzes.size() > 0){ %>
			<h5>Most Popular Quizzes:</h5><br/>
			<% for(int i = popularQuizzes.size() - 1; i >= 0 && i >= popularQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= popularQuizzes.get(i).getQuizID() %>"><%= popularQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= popularQuizzes.get(i).getDescription() %></p>
			<% } %>
		<% } %>
		
		<h3 class="auth-center">Recently Created Quizzes</h3>
		<% ArrayList<Quiz> recentQuizzes = quizManager.getMostRecentlyCreatedQuizzes(); %>
		<% if(recentQuizzes.size() > 0){ %>
			<h5>Most Recently Created Quizzes:</h5><br/>
			<% for(int i = recentQuizzes.size() - 1; i >= 0 && i >= recentQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= recentQuizzes.get(i).getQuizID() %>"><%= recentQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= recentQuizzes.get(i).getDescription() %></p>
			<% } %>
		<% } %>
		
		<h3 class="auth-center">Your Quiz History</h3>
		<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(userId); %>
		<% if(history.size() > 0){ %>
			<h5>Your Most Recent Taken Quizzes:</h5><br/>
			<% for(int i = history.size() - 1; i >= 0 && i >= history.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= history.get(i).getQuizId() %>"><%= history.get(i).getName() %></a></p>
				<p>Score: <%=quizManager.convertToPercStr(history.get(i).getScore(), history.get(i).getTotal()) %></p><br/>
			<% } %>
		<% } %>
		<a href="user_quiz_history.jsp?id=<%= userId %>"><button type="button">Show Full History</button></a>
		
		<h3 class="auth-center">Your Quizzes</h3>
		<% ArrayList<Quiz> yourQuizzes = userManager.getAuthoredQuizzes(userId); %>
		<% if(yourQuizzes.size() > 0){ %>
			<h5>Your Most Recent Created Quizzes:</h5><br/>
			<% for(int i = yourQuizzes.size() - 1; i >= 0 && i >= yourQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= yourQuizzes.get(i).getQuizID() %>"><%= yourQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= yourQuizzes.get(i).getDescription() %></p>
			<% } %>
		<% } %>
		
		<h3 class="auth-center">Achievements</h3>
			<ul>
			<% ArrayList<String> achievements = quizManager.getAchievements(userId); 
			String check = "I am the Greatest";
			%>
			<% 
			for (int i = 0 ; i < achievements.size() ; i++) { %>
				<% String description = achievements.get(i);
				String quizId = ""; %>
				<% if (description.contains(check)) { %>
					<% quizId = description.substring(check.length());
					description = check; %>
					<li><%=description%>: <%= quizManager.getQuizByID(Integer.parseInt(quizId)).getName() %></li>
				<%} else {%>
					<li><%= description %></li>
				<%}%>
			<%}%>
			</ul>
		
		<h3 class="auth-center">Your Friends</h3>
			<ul>
			<% 
			ArrayList<Integer> friendsList = friendsManager.getFriends(userId);
			%> 
			<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
				<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%></a></li>
				
			<%}%>
			</ul>
								
		<h3 class="auth-center">Pending Friend Requests</h3>
			<ul>
			<% 
			ArrayList<Integer> requestList = friendsManager.showFriendRequests(userId);
			%>
			<%
			for (int i = 0 ; i < requestList.size() ; i++) { %>
				<% 
				int otherUserId = requestList.get(i); 
				String otherUserEmail = userManager.getEmailByID(otherUserId);
				%>
					<li><a href="friend_homepage.jsp?friendEmail=<%=otherUserEmail%>"><%=userManager.getNameByID(requestList.get(i))%></a>
					<form action="FriendServlet" method="post">
						<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
					    <input type="hidden" value="Accept From Home Page" name="decision" />
					    <input type="submit" value="Accept Friend Request" />
				    </form>
				    <form action="FriendServlet" method="post">
						<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
						<input type="hidden" value="Reject From Home Page" name="decision" />
					    <input type="submit" value="Reject Friend Request" />
				    </form>
				</li>
			<%}%>
			</ul>
		
		<h3 class="auth-center">Search Users</h3>
			<form action="UserSearchServlet" method="post">
			<p><input type="text" name="info" />
			<input type="submit" value="Search" /></p>
			</form>
		
		<h3 class="auth-center">News Feed</h3>
		
		<% ArrayList<Quiz> quizTakenNewsfeed = friendsManager.quizTakenNewsfeed(userId); %>
		<% if(quizTakenNewsfeed.size() > 0){ %>
			<h5>Recent Quizzes Your Friends Took:</h5><br/>
			<ul>
			<% for(int i = quizTakenNewsfeed.size() - 1; i >= 0 && i >= quizTakenNewsfeed.size() - 5; i--){ %>
				<% Quiz currQuiz = quizTakenNewsfeed.get(i);%>
				<% int friendId = currQuiz.getQuizTakerId();%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId)%>"><%=userManager.getNameByID(friendId)%></a> took <a href="quiz_summary.jsp?quiz_id=<%= currQuiz.getQuizID() %>"><%= currQuiz.getName() %></a> and got <%= currQuiz.getScore() %></p>
				</li>
			<% } %>
			</ul>
		<% } %>
		
		<% ArrayList<Quiz> quizCreatedNewsfeed = friendsManager.quizCreatedNewsfeed(userId); %>
		<% if(quizCreatedNewsfeed.size() > 0){ %>
			<h5>Recent Quizzes Your Friends Created:</h5><br/>
			<ul>
			<% for(int i = quizCreatedNewsfeed.size() - 1; i >= 0 && i >= quizCreatedNewsfeed.size() - 5; i--){ %>
				<% Quiz madeQuiz = quizCreatedNewsfeed.get(i);%>
				<% int friendId = madeQuiz.getQuizTakerId();%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId)%>"><%=userManager.getNameByID(friendId)%></a> created a quiz: <a href="quiz_summary.jsp?quiz_id=<%= madeQuiz.getQuizID() %>"><%= madeQuiz.getName() %></a></p>
				</li>
			<% } %>
			</ul>
		<% } %>
		
		<% ArrayList<String> achievementEarnedNewsfeed = friendsManager.achievementEarnedNewsfeed(userId); %>
		<% if(achievementEarnedNewsfeed.size() > 0){ %>
			<h5>Recent Quizzes Your Friends Created:</h5><br/>
			<ul>
			<% for(int i = achievementEarnedNewsfeed.size() - 1; i >= 0 && i >= achievementEarnedNewsfeed.size() - 10; i = i-2){ %>
				<% String description = achievementEarnedNewsfeed.get(i);%>
				<% int friendId = Integer.parseInt(achievementEarnedNewsfeed.get(i-1));%>
				<% String greatest = "I am the Greatest";%>
				<% if (description.contains(greatest)) { %>
					<% 
					int quizId = Integer.parseInt(description.substring(greatest.length()));
					description = greatest; 
					%>
					<li>
					<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId)%>"><%=userManager.getNameByID(friendId)%></a> got an achievement award <%= description%> for <a href="quiz_summary.jsp?quiz_id=<%= quizId %>"><%= quizManager.getQuizByID(quizId).getName() %></a>.</p>
					</li>
				<% } else {%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId)%>"><%=userManager.getNameByID(friendId)%></a> got an achievement award <%= description%>.</p>
				</li>
				<% } %>
			<% } %>
			</ul>
		<% } %>
		
		<% ArrayList<Integer> becameFriendsNewsfeed = friendsManager.becameFriendsNewsfeed(userId); %>
		<% if(becameFriendsNewsfeed.size() > 0){ %>
			<h5>Recent Quizzes Your Friends Created:</h5><br/>
			<ul>
			<% for(int i = becameFriendsNewsfeed.size() - 1; i >= 0 && i >= becameFriendsNewsfeed.size() - 10; i = i-2){ %>
				<% int friendId1 = becameFriendsNewsfeed.get(i);%>
				<% int friendId2 = becameFriendsNewsfeed.get(i-1);%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId1)%>"><%=userManager.getNameByID(friendId1)%></a> became friends with <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId2)%>"><%=userManager.getNameByID(friendId2)%></a>.</p>
				</li>
			<% } %>
			</ul>
		<% } %>
		
		<h3 class="auth-center">Send Messages</h3>
			<form action="MessageServlet" method="post">
			<p>Send to: <input type="text" name="receiver" /></p>
			<p>Message: <input type="text" name="new message" />
			<input type="submit" value="Send" /></p>
			</form>
			<% if (messageStatus != null) { %>
				<% if (messageStatus.equals("sent")) { %>
					<p>Message Sent!</p>
				<% } %>
				<% if (messageStatus.equals("failed")) { %>
					<p>Failed to send message: Invalid user email</p>
				<% } %>
			<% } %>
		
		<h3 class="auth-center">Your Messages</h3>
			<ul>
			<% 
			ArrayList<String> messages = messageManager.getMessage(email);
			%>
			<%for (int i = messages.size() - 1 ; i >= 0 ; i-=2) { %>
				<li><a href="friend_homepage.jsp?friendEmail=<%=messages.get(i)%>"><%=messages.get(i)%></a>: <%=messages.get(i-1)%></li>
			<%}%>
			</ul>
	</body>
</html>