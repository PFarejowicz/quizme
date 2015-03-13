package hw6Quiz.manager;

import java.sql.*;
import java.util.*;

import hw6Quiz.model.Challenge;

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
			
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO messages (sender_id, receiver_id, data) VALUES(?, ?, ?)");
			prepStmt.setInt(1, senderId);
			prepStmt.setInt(2, receiverId);
			prepStmt.setString(3, message);
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

			while (rs.next()) {
				int senderId = rs.getInt("sender_id");
				String message = rs.getString("data");
				ResultSet resultSet = stmt2.executeQuery("SELECT * FROM users WHERE user_id = \"" + senderId + "\"");
				String senderEmail = "";
				if (resultSet.next()) {
					senderEmail = resultSet.getString("email");
				}
				messages.add(message);
				messages.add(senderEmail);
			}
			return messages;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendChallenge(int sender, int receiver, int quizId, int score) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO challenges (sender_id, receiver_id, quiz_id, score) VALUES(?, ?, ?, ?)");
			prepStmt.setInt(1, sender);
			prepStmt.setInt(2, receiver);
			prepStmt.setInt(3, quizId);
			prepStmt.setInt(4, score);
			prepStmt.executeUpdate();
			System.out.println("test"); 
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public ArrayList<Challenge> getChallenge(int receiver_id) {
		ArrayList<Challenge> challengeList = new ArrayList<Challenge>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM challenges WHERE receiver_id = \"" + receiver_id + "\"");
			while(rs.next()) {
				Challenge challenge = new Challenge(rs.getInt("sender_id"), rs.getInt("receiver_id"), rs.getInt("quid_id"), rs.getInt("score"));
				challengeList.add(challenge);
			}
			return challengeList;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return challengeList;
	}
	
	public void deleteChallenge(int sender_id, int receiver_id, int quiz_id, int score) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM challenges WHERE sender_id = \"" + sender_id + "\" AND receiver_id = \"" + receiver_id + "\" AND quiz_id = \"" + quiz_id + "\" AND score = \"" + score + "\"");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
