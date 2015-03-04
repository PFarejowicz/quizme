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
		System.out.println("test1");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + userEmail + "\"");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
			System.out.println("test2");

			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + friendEmail + "\"");
			if (rs.next()) {
				friendId = rs.getInt("user_id");
			}
			System.out.println("test3");

			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id = \"" + userId + "\"");
			if (rs.next()) {
				System.out.println("test4");

				Object obj = rs.getObject("friend_list");
				ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) obj);
				ObjectInputStream is = new ObjectInputStream(bs);
				friendsList = (ArrayList<Integer>) is.readObject();
				System.out.println("test5");

			} else {
				friendsList = new ArrayList<Integer>();
				System.out.println("test6");

			}
			friendsList.add(friendId);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(friendsList);
			System.out.println("test7");

			os.close();
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO friends VALUES(\"" + userId + "\",\"" + bs.toByteArray() + "\")");
			prepStmt.executeUpdate();
			System.out.println("test8");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getFriends(int userId) {
		Statement stmt;
		ArrayList<Integer> friendsList = new ArrayList<Integer>();
		System.out.println("try1");

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM friends WHERE user_id = \"" + userId + "\"");
			System.out.println("try2");

			if (rs.next()) {
				System.out.println("try3");

				Object obj = rs.getBlob("friend_list");
				System.out.println("try4");

				ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) obj);
				System.out.println("try5");

				ObjectInputStream is = new ObjectInputStream(bs);
				System.out.println("try6");

				friendsList = (ArrayList<Integer>) is.readObject();
				System.out.println("try7");

			}
			return friendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
