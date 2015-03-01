package hw6Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageManager {
	
	private Connection con; 
	private ResultSet rs; 

	public MessageManager(Connection con) {
		this.con = con; 
	}
	
	public void addMessage() {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO messages VALUES(?, ?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
