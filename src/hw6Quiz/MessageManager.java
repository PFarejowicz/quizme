package hw6Quiz;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageManager {
	
	private Connection con; 
	private ResultSet rs; 

	public MessageManager(Connection con) {
		this.con = con; 
	}
	
	public void addMessage(String message, String sender, String receiver) {
		Statement stmt;
		int senderId = 0;
		int receiverId = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + sender + "\"");
			if (rs.next()) {
				senderId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + receiver + "\"");
			if (rs.next()) {
				receiverId = rs.getInt("user_id");
			}
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO messages VALUES(" + senderId + ", " + receiverId + ", " + message + ")");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
