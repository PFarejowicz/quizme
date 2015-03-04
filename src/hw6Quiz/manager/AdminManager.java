package hw6Quiz.manager;
import hw6Quiz.model.*;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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
	
	public ArrayList<String> getAnnouncements(){
		ArrayList<String> announcements = new ArrayList<String>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM announcements");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				announcements.add(rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return announcements;
	}
	
	public void removeAnnouncement(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM announcements WHERE announcement_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<User> getUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				User user = new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("name"), rs.getBoolean("admin_privilege"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public void removeUserAccount(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM users WHERE user_id = " + id);
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
	
	public ArrayList<Quiz> getQuizzes(){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM quizzes");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"));
				quizzes.add(quiz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public void clearHistoryForQuiz(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM quiz_history WHERE quiz_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeQuiz(int id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM quizzes WHERE quiz_id = " + id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
