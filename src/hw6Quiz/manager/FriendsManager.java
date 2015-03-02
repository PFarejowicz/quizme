package hw6Quiz.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FriendsManager {
	
	private Connection con; 
	private ResultSet rs; 

	public FriendsManager(Connection con) {
		this.con = con; 
	}
	
	public void addFriend() {
		
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friends VALUES(?, ?)");
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
