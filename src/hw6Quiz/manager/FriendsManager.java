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
				if (rs.getInt(2) == friendId) alreadyAdded = true;
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId + "\"");
			while (rs.next()) {
				if (rs.getInt(1) == friendId) alreadyAdded = true;
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
				friendsList.add(rs.getInt(2));
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId + "\"");
			while (rs.next()) {
				friendsList.add(rs.getInt(1));
			}
			return friendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendFriendRequest() {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friend_request VALUES(?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> showFriendRequests(int userId) {
		Statement stmt;
		ArrayList<Integer> friendsList = new ArrayList<Integer>();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id1 = \"" + userId + "\"");
			System.out.println("try2");
			while (rs.next()) {
				friendsList.add(rs.getInt(2));
			}
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id2 = \"" + userId + "\"");
			while (rs.next()) {
				friendsList.add(rs.getInt(1));
			}
			return friendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
