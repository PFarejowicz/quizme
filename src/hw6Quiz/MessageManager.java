package hw6Quiz;

import java.sql.*;
import java.util.*;

public class MessageManager {
	
	private Connection con; 
	private ResultSet rs; 

	public MessageManager(Connection con) {
		this.con = con; 
	}
	
	public void sendMessage(String message, String sender, String receiver) {
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
			System.out.println(senderId);
			System.out.println(receiverId);
			System.out.println(message);

			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO messages VALUES(\"" + senderId + "\",\"" + receiverId + "\",\"" + message + "\")");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public ArrayList<String> getMessage (String receiver) {
		Statement stmt;
		Statement stmt2;
		int receiverId = 0;
		ArrayList<String> messages = new ArrayList<String>();
		try {
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + receiver + "\"");
			if (rs.next()) {
				receiverId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM messages WHERE receiver_id = \"" + receiverId + "\"");
			System.out.println(receiverId);

			while (rs.next()) {
				int senderId = rs.getInt(1);
				String message = rs.getString(3);
				System.out.println(senderId);
				System.out.println(message);

				ResultSet resultSet = stmt2.executeQuery("SELECT * FROM users WHERE user_id = \"" + senderId + "\"");
				String senderEmail = "";
				if (resultSet.next()) {
					senderEmail = resultSet.getString("email");
				}
//				System.out.println(senderEmail);
				messages.add(message);
				messages.add(senderEmail);
			}
			System.out.println("end");
			return messages;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
