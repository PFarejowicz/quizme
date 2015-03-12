package hw6Quiz.manager;

import hw6Quiz.model.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class FriendsManager {

	private Connection con; 
	private ResultSet rs; 

	public FriendsManager(Connection con) {
		this.con = con; 
	}

	public void addFriend(String userEmail, String friendEmail) {
		Statement stmt;
		int userId = 0;
		int friendId = 0;
		boolean alreadyAdded = false;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + userEmail + "\"");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}

			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + friendEmail + "\"");
			if (rs.next()) {
				friendId = rs.getInt("user_id");
			}

			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + userId + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id2") == friendId) alreadyAdded = true;
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id1") == friendId) alreadyAdded = true;
			}
			
			if (!alreadyAdded) {
			    Calendar calendar = Calendar.getInstance();
			    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friends (user_id1, user_id2, date_time) VALUES(?, ?, ?)");
				prepStmt.setInt(1, userId);
				prepStmt.setInt(2, friendId);
				prepStmt.setTimestamp(2, timeStamp);
				prepStmt.executeUpdate();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getFriends(int userId) {
		Statement stmt;
		ArrayList<Integer> friendsList = new ArrayList<Integer>();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + userId + "\"");
			while (rs.next()) {
				friendsList.add(rs.getInt("user_id2"));
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId + "\"");
			while (rs.next()) {
				friendsList.add(rs.getInt("user_id1"));
			}
			return friendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendFriendRequest(int from, int to) {
		Statement stmt;
		try {
			boolean alreadyRequested = false;
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + from + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id2") == to) alreadyRequested = true;
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + from + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id1") == to) alreadyRequested = true;
			}
			
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE from_user_id = \"" + from + "\"");
			while(rs.next()) {
				if (rs.getInt("to_user_id") == to) {
					alreadyRequested = true;
				}
			}
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \"" + from + "\"");
			while(rs.next()) {
				if (rs.getInt("from_user_id") == to) {
					alreadyRequested = true;
				}
			}
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \"" + from + "\"");

			if (!alreadyRequested) {
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friend_requests (from_user_id, to_user_id) VALUES(?, ?)");
				prepStmt.setInt(1, from);
				prepStmt.setInt(2, to);
				prepStmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> showFriendRequests(int userId) {
		Statement stmt;
		ArrayList<Integer> requestList = new ArrayList<Integer>();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \"" + userId + "\"");
			while (rs.next()) {
				requestList.add(rs.getInt(2));
			}
			return requestList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Integer> findMutualFriends() {
		ArrayList<Integer> mutuals = new ArrayList<Integer>();
		return mutuals;
	}
	
	public boolean isFriend(int userId1, int userId2) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + userId1 + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id2") == userId2) return true;
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId1 + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id1") == userId2) return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int checkFriendStatus(int userId1, int userId2) {
		int areFriends = 1;
		int user1Requested = 2;
		int user2Requested = 3;
		int noRequests = 4;
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + userId1 + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id2") == userId2) return areFriends;
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId1 + "\"");
			while (rs.next()) {
				if (rs.getInt("user_id1") == userId2) return areFriends;
			}
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE from_user_id = \"" + userId1 + "\"");
			while(rs.next()) {
				if(rs.getInt("to_user_id") == userId2) return user1Requested;
			}
			rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \"" + userId1 + "\"");
			while(rs.next()) {
				if(rs.getInt("from_user_id") == userId2) return user2Requested;
			}
			return noRequests;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteFriendRequest(String userEmail, String friendRequestEmail) {
		Statement stmt;
		int userId = 0;
		int friendRequestId = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + userEmail + "\"");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + friendRequestEmail + "\"");
			if (rs.next()) {
				friendRequestId = rs.getInt("user_id");
			}
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM friend_requests WHERE from_user_id = \"" + friendRequestId + "\" AND to_user_id = \"" + userId + "\"");
			prepStmt.executeUpdate();
			prepStmt = con.prepareStatement("DELETE FROM friend_requests WHERE from_user_id = \"" + userId + "\" AND to_user_id = \"" + friendRequestId + "\"");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void unfriend(String userEmail, String friendEmail) {
		Statement stmt;
		int userId = 0;
		int friendId = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + userEmail + "\"");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + friendEmail + "\"");
			if (rs.next()) {
				friendId = rs.getInt("user_id");
			}
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM friends WHERE user_id1 = \"" + userId + "\" AND user_id2 = \"" + friendId + "\"");
			prepStmt.executeUpdate();
			prepStmt = con.prepareStatement("DELETE FROM friends WHERE user_id1 = \"" + friendId + "\" AND user_id2 = \"" + userId + "\"");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Quiz> quizTakenNewsfeed(int user_id) {
		ArrayList<Quiz> recentlyTaken = new ArrayList<Quiz>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history");
			while(rs.next()) {
				if (isFriend(user_id, rs.getInt("author_id"))) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getTimestamp("time_taken"), rs.getInt("score"), rs.getInt("user_id"));
					recentlyTaken.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyTaken;
	}
	
	public ArrayList<Quiz> quizCreatedNewsfeed(int user_id) {
		
		ArrayList<Quiz> recentlyCreated = new ArrayList<Quiz>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes");
			while(rs.next()) {
				if (isFriend(user_id, rs.getInt("author_id"))) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getTimestamp("date_time"), rs.getInt("author_id"));
					recentlyCreated.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyCreated;
	}
	
	public ArrayList<String> achievementEarnedNewsfeed(int user_id) {
		ArrayList<String> recentAchievements = new ArrayList<String>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM achievements");
			while(rs.next()) {
				if (isFriend(user_id, rs.getInt("user_id"))) {
					recentAchievements.add(rs.getInt("user_id") + "");
					recentAchievements.add(rs.getString("description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentAchievements;
	}
	
	public ArrayList<Integer> becameFriendsNewsfeed(int user_id) {
		ArrayList<Integer> friends = new ArrayList<Integer>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM friends");
			while(rs.next()) {
				if (isFriend(user_id, rs.getInt("user_id1"))) {
					friends.add(rs.getInt("user_id2"));
					friends.add(rs.getInt("user_id1"));
				} else if (isFriend(user_id, rs.getInt("user_id2"))) {
					friends.add(rs.getInt("user_id1"));
					friends.add(rs.getInt("user_id2"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
}
