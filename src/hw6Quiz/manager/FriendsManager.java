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
		ArrayList<Integer> friendsList;
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
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id = \"" + userId + "\"");
			if (rs.next()) {
				Object obj = rs.getObject("friend_list");
				ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) obj);
				ObjectInputStream is = new ObjectInputStream(bs);
				friendsList = (ArrayList<Integer>) is.readObject();
			} else {
				friendsList = new ArrayList<Integer>();
			}
			friendsList.add(friendId);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(friendsList);
			os.close();
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friends VALUES(\"" + userId + "\",\"" + bs.toByteArray() + "\")");
			//				prepStmt.setObject(7, bs.toByteArray());
			prepStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		//		Statement stmt; 
		//		try {
		//			stmt = con.createStatement();
		//			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
		//			if (rs.next()) {
		//				user.setPassword(rs.getString("password"));
		//			}
		//			rs = stmt.executeQuery("SELECT * FROM friends WHERE email = \"" + email + "\"");
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}

	}

	public void addFriendRequest() {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friend_request VALUES(?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
