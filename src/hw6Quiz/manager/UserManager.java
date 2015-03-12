package hw6Quiz.manager;

import hw6Quiz.model.*;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class UserManager {

	private Connection con; 
	private ResultSet rs; 
	
	public UserManager(Connection con) {
		this.con = con; 
	}

	public boolean containsUser(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			return (rs.next());				// not empty
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean hasUsers(){
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users");
			return (rs.next());				// not empty
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Integer> findUsers(int userId, String info) {
		ArrayList<Integer> possibleUsers = new ArrayList<Integer>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email LIKE \"%" + info + "%\" OR name LIKE \"%" + info + "%\"");
			while (rs.next()) {
				if (rs.getInt("user_id") != userId) {
					possibleUsers.add(rs.getInt("user_id"));
				}
			}
			return possibleUsers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return possibleUsers;
	}
	
	public boolean isAdmin(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			if(rs.next()){
				return rs.getBoolean("admin_privilege");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public boolean checkPassword(String email, String password) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
			prepStmt.setString(1, email);										// fill in prepared statement
			rs = prepStmt.executeQuery();
			if (rs.next()) {													// validate email
				String salt = rs.getString("salt");
				MessageDigest md = MessageDigest.getInstance("SHA");
				password = password + salt; 									// adding salt to original password
				byte[] mdbytes = md.digest(password.getBytes());				// hash the password + salt 
				password = UserManager.hexToString(mdbytes);					// convert back to string
				return rs.getString("password").equals(password);				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	

	public void addUser(String email, String password, String name) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO users (email, password, salt, name, admin_privilege) VALUES(?, ?, ?, ?, ?)");
			prepStmt.setString(1, email);
			prepStmt.setString(4, name);
			
			final Random r = new SecureRandom();
			byte[] saltBytes = new byte[32];			
			r.nextBytes(saltBytes);											// generate salt
			String salt = UserManager.hexToString(saltBytes);
			prepStmt.setString(3, salt);
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			password = password + salt; 									// adding salt to original password
			byte[] mdbytes = md.digest(password.getBytes());				// hash the password + salt 
			password = UserManager.hexToString(mdbytes);					// convert back to string	
			prepStmt.setString(2, password);
			prepStmt.setBoolean(5, false);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAdmin(String email, String password, String name) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO users (email, password, salt, name, admin_privilege) VALUES(?, ?, ?, ?, ?)");
			prepStmt.setString(1, email);
			prepStmt.setString(4, name);
			
			final Random r = new SecureRandom();
			byte[] saltBytes = new byte[32];			
			r.nextBytes(saltBytes);											// generate salt
			String salt = UserManager.hexToString(saltBytes);
			prepStmt.setString(3, salt);
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			password = password + salt; 									// adding salt to original password
			byte[] mdbytes = md.digest(password.getBytes());				// hash the password + salt 
			password = UserManager.hexToString(mdbytes);					// convert back to string	
			prepStmt.setString(2, password);
			prepStmt.setBoolean(5, true);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getIDByEmail(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			if(rs.next()){
				return rs.getInt("user_id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getEmailByID(int id) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = \"" + id + "\"");
			if(rs.next()){
				return rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNameByID(int user_id) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = \"" + user_id + "\"");
			if(rs.next()){
				return rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return a list of quiz history of user 
	 * @param user_id user ID
	 * @return arraylist quiz history
	 */
	public ArrayList<QuizHistory> getQuizHistoryById(int user_id){
		ArrayList<QuizHistory> history = new ArrayList<QuizHistory>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = " + user_id);
			while(rs.next()){
				QuizHistory qh = new QuizHistory(rs.getInt("quiz_history_id"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("score"), rs.getInt("total"), rs.getInt("rating"), rs.getString("review"), rs.getString("name"), rs.getTimestamp("date_time"));
				history.add(qh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}
	
	/**
	 * Return a list of quizzes the user has created
	 * @param user_id user ID 
	 * @return quiz list
	 */
	public ArrayList<Quiz> getAuthoredQuizzes(int user_id) {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes WHERE author_id = " + user_id);
			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"));
				quizList.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	/**
	 * Get top score of a user in a quiz
	 * @param user_id user ID
	 * @param quiz_id quiz ID
	 * @return highscore, -1 if user has never taken quiz
	 */
	public int getTopScore(int user_id, int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE user_id = " + user_id + " AND quiz_id = " + quiz_id);
			int highScore = 0;
			while (rs.next()) {
				if (rs.getInt("score") > highScore) {
					highScore = rs.getInt("score");
				}
			}
			return highScore;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<Quiz> quizTakenNewsfeed(int user_id) {
		ArrayList<Quiz> recentlyTaken = new ArrayList<Quiz>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history ORDER BY time_taken DESC");
			while(rs.next()) {
				if (rs.getInt("user_id") == user_id) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getTimestamp("time_taken"), rs.getInt("score"));
					recentlyTaken.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyTaken;
	}
	
	public ArrayList<Quiz> quizCreatedNewsfeed(int user_id) {
		ArrayList<Quiz> recentlyCreated = new ArrayList<Quiz>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes ORDER BY date_time DESC");
			while(rs.next()) {
				if (rs.getInt("author_id") == user_id) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getTimestamp("date_time"));
					recentlyCreated.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyCreated;
	}
	
	public ArrayList<String> achievementEarnedNewsfeed(int user_id) {
		ArrayList<String> recentAchievements = new ArrayList<String>();
		return recentAchievements;
	}
	
	
	
	
}
