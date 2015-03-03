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
//		int senderId = 0;
		int receiverId = 0;
		ArrayList<Integer> friendsList;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + friendEmail + "\"");
			if (rs.next()) {
				receiverId = rs.getInt("user_id");
				if (rs.getBlob("friends") == null) {
					friendsList = new ArrayList<Integer>();
				} else {
					Object obj = rs.getObject("friends");
					ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) obj);
					ObjectInputStream is = new ObjectInputStream(bs);
					friendsList = (ArrayList<Integer>) is.readObject();
				}
				friendsList.add(receiverId);
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bs);
				os.writeObject(friendsList);
				os.close();
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO users (friends) VALUES(?)");
				prepStmt.setObject(7, bs.toByteArray());
				prepStmt.executeUpdate();
			}		
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
