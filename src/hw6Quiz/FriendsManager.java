package hw6Quiz;

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
