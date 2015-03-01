package hw6Quiz;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class AdminManager {
	
	private Connection con; 
	private ResultSet rs;

	public AdminManager(Connection con) {
		this.con = con;
		// TODO Auto-generated constructor stub
	}
	
	public void addAnnouncement(String announcement){
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO announcements (description) VALUES(?)");
			prepStmt.setString(1, announcement);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeAnnouncement(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM announcements WHERE announcement_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeUserAccount(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM users WHERE user_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearHistoryForQuiz(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM quiz_history WHERE quiz_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void promoteToAdmin(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET admin_privilege = true WHERE user_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getNumberOfUsers(){
		int count = 0;
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getNumberOfQuizzes(){
		int count = 0;
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM quiz_history");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
