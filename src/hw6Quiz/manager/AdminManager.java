package hw6Quiz.manager;
import hw6Quiz.model.*;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
	
	public void removeAnnouncement(int announcement_id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM announcements WHERE announcement_id = " + announcement_id);
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
	
	public ArrayList<User> getAdmins(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users WHERE admin_privilege = 1");
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
	
	public ArrayList<User> getNonAdmins(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users WHERE admin_privilege = 0");
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
	
	/**
	 * Given a user_id, remove all associated quizzes, questions, history, friends, messages, and challenges associated with taht user
	 * @param id user id 
	 */
	public void removeUserAccount(int user_id){
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM users WHERE user_id = " + user_id);
			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE author_id = " + user_id);
			while (rs.next()) {
				clearHistoryForQuiz(rs.getInt("quiz_id"));
				clearQuestionsForQuiz(rs.getInt("quiz_id"));
				removeQuiz(rs.getInt("quiz_id"));
			}
			stmt.executeUpdate("DELETE FROM friends WHERE user_id1 = " + user_id);
			stmt.executeUpdate("DELETE FROM friends WHERE user_id2 = " + user_id);
			stmt.executeUpdate("DELETE FROM friend_requests WHERE from_user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM friend_requests WHERE to_user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM achievements WHERE user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM challenges WHERE sender_id = " + user_id);
			stmt.executeUpdate("DELETE FROM challenges WHERE receiver_id = " + user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void promoteToAdmin(int user_id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET admin_privilege = true WHERE user_id = " + user_id);
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
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				quizzes.add(quiz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public ArrayList<Quiz> getReportedQuizzes(){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM quizzes WHERE reported = 1");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				quizzes.add(quiz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public void clearHistoryForQuiz(int quiz_id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM quiz_history WHERE quiz_id = " + quiz_id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove all questions associated to a quiz given quiz id 
	 * @param quiz_id quiz id 
	 */
	public void clearQuestionsForQuiz(int quiz_id) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM questions WHERE quiz_id = " + quiz_id);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove quiz given quiz id
	 * @param quiz_id quiz id
	 */
	public void removeQuiz(int quiz_id){
		try {
			PreparedStatement prepStmt = con.prepareStatement("DELETE FROM quizzes WHERE quiz_id = " + quiz_id);
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
