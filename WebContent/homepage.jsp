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
		
		<p class="auth-center">
			<a href="create_quiz.jsp"><button type="button">Create a Quiz</button></a>
			<a href="quiz_search.jsp"><button type="button">Find a Quiz</button></a>
		</p>
		
		
		<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
		<% if(announcements.size() > 0){ %>
		<div class="card">
		<h5>Announcements:</h5>
		<ul>
			<% for(int i = 0; i < announcements.size(); i++){ %>
			<li><%= announcements.get(i) %></li>
			<% } %>
		</ul>
		</div>
		<% } %>
		
		<% ArrayList<Quiz> popularQuizzes = quizManager.getPopularQuizzes(); %>
		<% if(popularQuizzes.size() > 0){ %>
			<div class="card">
			<h5>Most Popular Quizzes:</h5><br/>
			<% for(int i = popularQuizzes.size() - 1; i >= 0 && i >= popularQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= popularQuizzes.get(i).getQuizID() %>"><%= popularQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= popularQuizzes.get(i).getDescription() %></p>
				<br/>
			<% } %>
			</div>
		<% } %>
		
		<% ArrayList<Quiz> recentQuizzes = quizManager.getMostRecentlyCreatedQuizzes(); %>
		<% if(recentQuizzes.size() > 0){ %>
			<div class="card">
			<h5>Most Recently Created Quizzes:</h5><br/>
			<% for(int i = recentQuizzes.size() - 1; i >= 0 && i >= recentQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= recentQuizzes.get(i).getQuizID() %>"><%= recentQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= recentQuizzes.get(i).getDescription() %></p>
				<br/>
			<% } %>
			</div>
		<% } %>
		
		<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(userId); %>
		<% if(history.size() > 0){ %>
			<div class="card">
			<h5>Your Most Recent Taken Quizzes:</h5><br/>
			<% for(int i = history.size() - 1; i >= 0 && i >= history.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= history.get(i).getQuizId() %>"><%= history.get(i).getName() %></a></p>
				<p>Score: <%=quizManager.convertToPercStr(history.get(i).getScore(), history.get(i).getTotal()) %></p><br/>
				<br/>
			<% } %>
			<p><a href="user_quiz_history.jsp?id=<%= userId %>"><button type="button">Show Full History</button></a></p>
			</div>
		<% } %>
		
		<% ArrayList<Quiz> yourQuizzes = userManager.getAuthoredQuizzes(userId); %>
		<% if(yourQuizzes.size() > 0){ %>
			<div class="card">
			<h5>Your Most Recent Created Quizzes:</h5><br/>
			<% for(int i = yourQuizzes.size() - 1; i >= 0 && i >= yourQuizzes.size() - 3; i--){ %>
				<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= yourQuizzes.get(i).getQuizID() %>"><%= yourQuizzes.get(i).getName() %></a></p>
				<p>Description: <%= yourQuizzes.get(i).getDescription() %></p>
				<br/>
			<% } %>
			</div>
		<% } %>
		
		
			
			<% ArrayList<String> achievements = quizManager.getAchievements(userId); 
			String check = "I am the Greatest";
			%>
			<% if(achievements.size() > 0){ %>
			<div class="card">
			<h5>Achievements:</h5>
			<ul>
			<% 
			for (int i = 0 ; i < achievements.size() ; i++) { %>
				<% String description = achievements.get(i);
				int quizId = 0; %>
				<% if (description.contains(check)) { %>
					<% quizId = Integer.parseInt(description.substring(check.length()));
					description = check; %>
					<%if (quizManager.quizExists(quizId)) { %>
						<li><%=description%>: <a href="quiz_summary.jsp?quiz_id=<%= quizId %>"><%= quizManager.getQuizByID(quizId).getName() %></a></li>	
					<% } %>
				<%} else {%>
					<li><%= description %></li>
				<%}%>
			<%}%>
			</ul>
			</div>
			<% } %>
			
		
		
		
			
			<% 
			ArrayList<Integer> friendsList = friendsManager.getFriends(userId);
			%>
			<% if(friendsList.size() > 0){ %>
				<div class="card">
					<h5>Your Friends:</h5>
					<ul>
						<% for(int i = 0 ; i < friendsList.size() ; i++) { %>
							<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%></a></li>
						<% } %>
					</ul>
				</div>
			<% } %>
		
			
		
			
			<% 
			ArrayList<Integer> requestList = friendsManager.showFriendRequests(userId);
			%>
			<% if(requestList.size() > 0){ %>
			<div class="card">					
			<h5>Pending Friend Requests:</h5>
			<ul>
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
			</div>
			<% } %>
		
		
		<div class="card">
		<h5>Search Users</h5>
			<form action="UserSearchServlet" method="post">
			<p><input type="text" name="info" />
			<input type="submit" value="Search" /></p>
			</form>
		</div>
		
		
		<h3 id="newsfeed">News Feed</h3>
		
		<% ArrayList<Quiz> quizTakenNewsfeed = friendsManager.quizTakenNewsfeed(userId); %>
		<% if(quizTakenNewsfeed.size() > 0){ %>
			<div class="card">
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
			</div>
		<% } %>
		
		<% ArrayList<Quiz> quizCreatedNewsfeed = friendsManager.quizCreatedNewsfeed(userId); %>
		<% if(quizCreatedNewsfeed.size() > 0){ %>
			<div class="card">
			<h5>Recent Quizzes Your Friends Created:</h5><br/>
			<ul>
			<% for(int i = quizCreatedNewsfeed.size() - 1; i >= 0 && i >= quizCreatedNewsfeed.size() - 5; i--){ %>
				<% Quiz madeQuiz = quizCreatedNewsfeed.get(i);%>
				<% int friendId = madeQuiz.getAuthorID();%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId)%>"><%=userManager.getNameByID(friendId)%></a> created a quiz: <a href="quiz_summary.jsp?quiz_id=<%= madeQuiz.getQuizID() %>"><%= madeQuiz.getName() %></a></p>
				</li>
			<% } %>
			</ul>
			</div>
		<% } %>
		
		<% ArrayList<String> achievementEarnedNewsfeed = friendsManager.achievementEarnedNewsfeed(userId); %>
		<% if(achievementEarnedNewsfeed.size() > 0){ %>
			<div class="card">
			<h5>Recent Achievements Your Friends Earned:</h5><br/>
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
			</div>
		<% } %>
		
		<% ArrayList<Integer> becameFriendsNewsfeed = friendsManager.becameFriendsNewsfeed(userId); %>
		<% if(becameFriendsNewsfeed.size() > 0){ %>
			<div class="card">
			<h5>Your Friend's Social Activities:</h5><br/>
			<ul>
			<% for(int i = becameFriendsNewsfeed.size() - 1; i >= 0 && i >= becameFriendsNewsfeed.size() - 10; i = i-2){ %>
				<% int friendId1 = becameFriendsNewsfeed.get(i);%>
				<% int friendId2 = becameFriendsNewsfeed.get(i-1);%>
				<% if (friendId2 == userId) { %>
					<li>
					<p>You and <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId1)%>"><%=userManager.getNameByID(friendId1)%></a> are now friends!</p>
					</li>
				<% } else {%>
				<li>
				<p><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId1)%>"><%=userManager.getNameByID(friendId1)%></a> became friends with <a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendId2)%>"><%=userManager.getNameByID(friendId2)%></a>.</p>
				</li>
				<% } %>
			<% } %>
			</ul>
			</div>
		<% } %>
		
		<div class="card">
		<h5>Send Messages</h5>
			<form action="MessageServlet" method="post">
			<p>Send to: <input type="text" name="receiver" /></p>
			<br/>
			<p>Message: <input type="text" name="new message" />
			<input type="submit" value="Send" /></p>
			</form>
			<% if (messageStatus != null) { %>
				<% if (messageStatus.equals("sent")) { %>
					<p>Message Sent!</p>
					<% request.getSession().setAttribute("message status", "none"); %>
				<% } %>
				<% if (messageStatus.equals("failed")) { %>
					<p>Failed to send message: Invalid user email</p>
					<% request.getSession().setAttribute("message status", "none"); %>
				<% } %>
			<% } %>
		</div>
		
		
			
			<% 
			ArrayList<String> messages = messageManager.getMessage(email);
			%>
			<% if(messages.size() > 0){ %>
			<div class="card">
			<h5>Your Messages:</h5>
			<ul>
			<%for (int i = messages.size() - 1 ; i >= 0 ; i-=2) { %>
				<li><a href="friend_homepage.jsp?friendEmail=<%=messages.get(i)%>"><%=messages.get(i)%></a>: <%=messages.get(i-1)%></li>
			<%}%>
			</ul>
			</div>
			<% } %>
		
			
		
			
			<% ArrayList<Challenge> challenges = messageManager.getChallenge(userId); %>
			<% if(challenges.size() > 0){ %>
			<div class="card">
			<h5>Challenges</h5>
			<ul>
			<%
			for (int i = 0 ; i < challenges.size() ; i++) { %>
				<% 
				Challenge challenge = challenges.get(i);
				int quizId = challenge.getQuizId();
				int senderId = challenge.getSenderId();
				String sender = userManager.getNameByID(senderId);
				int score = challenge.getScore();
				%>
				<li>
				<p><%= sender %> got a score of <%= score %> in <a href="quiz_summary.jsp?quiz_id=<%=quizId%>"><%=quizManager.getQuizNameByID(quizId)%></a>. Accept the challenge to get a new record!</p>
					<form action="ChallengeServlet" method="post">
						<input type="hidden" value=<%=senderId%> name="sender_id" />
						<input type="hidden" value=<%=userId%> name="receiver_id" />
						<input type="hidden" value=<%=quizId%> name="quiz_id" />
						<input type="hidden" value=<%=score%> name="score" />
					    <input type="submit" value="Accept Challenge" name="decision" />
				    </form>
				    <form action="ChallengeServlet" method="post">
						<input type="hidden" value=<%=senderId%> name="sender_id" />
						<input type="hidden" value=<%=userId%> name="receiver_id" />
						<input type="hidden" value=<%=quizId%> name="quiz_id" />
						<input type="hidden" value=<%=score%> name="score" />
					    <input type="submit" value="Reject Challenge"  name="decision"/>
				    </form>
				</li>
			<%}%>
			</ul>
			</div>
			<% } %>
			
			
	</body>
</html>