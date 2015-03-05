package hw6Quiz.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friends VALUES(\"" + userId + "\",\"" + friendId + "\")");
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
	
}
