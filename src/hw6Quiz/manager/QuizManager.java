package hw6Quiz.manager;

import hw6Quiz.model.Quiz;
import hw6Quiz.model.QuizHistory;
import hw6Quiz.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.sql.Date;

public class QuizManager {
	
	private Connection con; 

	public QuizManager(Connection con) {
		this.con = con; 
	}
	
	/**
	 * Add quiz to database
	 * @param name name
	 * @param description description
	 * @param author author
	 * @param random random order
	 * @param pages single or multiple
	 * @param correction immediate correction or not
	 * @param points total points
	 */
	public void addQuiz(String name, String description, int author, boolean random, boolean pages, boolean correction, int points) {
		try {
		    Calendar calendar = Calendar.getInstance();
		    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO quizzes (name, description, author_id, random_order, multiple_pages, immediate_correction, date_time, points) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			insertStmt.setString(1, name);
			insertStmt.setString(2, description);
			insertStmt.setInt(3, author);
			insertStmt.setBoolean(4, random);
			insertStmt.setBoolean(5, pages);
			insertStmt.setBoolean(6, correction);
			insertStmt.setTimestamp(7, timeStamp);
			insertStmt.setInt(8, points);
			insertStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get quiz ID given name 
	 * @param name name
	 * @return quiz ID
	 */
	public int getIDByName(String name) {
		try {
			PreparedStatement selectStmt = con.prepareStatement("SELECT * FROM quizzes WHERE name = ?");
			selectStmt.setString(1, name);
			ResultSet rs = selectStmt.executeQuery();
			rs.next();
			System.out.println(rs.getInt("quiz_id"));
			return rs.getInt("quiz_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Get quiz object given ID
	 * @param quiz_id quiz ID 
	 * @return
	 */
	public Quiz getQuizByID(int quiz_id) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE quiz_id = \"" + quiz_id + "\"");
			if(rs.next()){
				Quiz quiz = new Quiz(quiz_id, rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"));
				return quiz;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all the quizzes
	 * @return quizzes ordered by name
	 */
	public ArrayList<Quiz> getQuizzes() {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes ORDER BY name");
			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"));
				quizList.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	/**
	 * Updates the number of total points of a quiz
	 * @param quiz_id quiz ID
	 * @param points total points
	 */
	public void updateQuizPoints(int quiz_id, int points) {
		try {
			PreparedStatement updateStmt = con.prepareStatement("UPDATE quizzes SET points = ? WHERE quiz_id = ?");
			updateStmt.setInt(1, points);
			updateStmt.setInt(2, quiz_id);
			updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get total points in a quiz
	 * @param quiz_id quiz ID 
	 * @return total points
	 */
	public int getQuizPoints(int quiz_id) {
		try {
			PreparedStatement selectStmt = con.prepareStatement("SELECT * FROM quizzes WHERE quiz_id = ?");
			selectStmt.setInt(1, quiz_id);
			ResultSet rs = selectStmt.executeQuery();
			rs.next();
			return rs.getInt("points");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Given a score and the total, it converts it into a percentage in string
	 * @param score score
	 * @param total total score
	 * @return percentage in string
	 */
	public String convertToPercStr(int score, int total) {
		return String.format("%.2f", (float) score / (float) total * 100) + "%";
	}
	
	/**
	 * Adds quiz result to quiz history
	 * @param quiz_id quiz ID
	 * @param user_id user ID
	 * @param score score of quiz
	 */
	public void addQuizResult(int quiz_id, int user_id, int score, int total, int rating, String review, String name) {
		try {
			Calendar calendar = Calendar.getInstance();
		    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO quiz_history (quiz_id, user_id, score, total, rating, review, name, date_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			insertStmt.setInt(1, quiz_id);
			insertStmt.setInt(2, user_id);
			insertStmt.setInt(3, score);
			insertStmt.setInt(4, total);
			insertStmt.setInt(5, rating);
			insertStmt.setString(6, review);
			insertStmt.setString(7, name);
			insertStmt.setTimestamp(8, timeStamp);
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ArrayList<QuizHistory> getQuizHistory(int quiz_id, int user_id, String type) {
		ArrayList<QuizHistory> history = new ArrayList<QuizHistory>();
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, -24);
			Timestamp yesterTimeStamp = new Timestamp(calendar.getTime().getTime());
			
			Statement selectStmt = con.createStatement();
			ResultSet rs = null;
			if (type.equals("past performance")) {
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" AND user_id = \"" + user_id + "\" ORDER BY date_time DESC");
			} else if (type.equals("all time high") || type.equals("last day high")) {
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" ORDER BY score DESC");
			} else if (type.equals("recent")) {
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" ORDER BY date_time DESC");
			}
			while (rs.next()){
				QuizHistory qh = new QuizHistory(rs.getInt("quiz_history_id"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("score"), rs.getInt("total"), rs.getInt("rating"), rs.getString("review"), rs.getString("name"), rs.getTimestamp("date_time"));
				if (type.equals("last day high")) {
					Timestamp timeStamp = new Timestamp(((Date) rs.getDate("date_time")).getTime());
					if (timeStamp.after(yesterTimeStamp)) {
						history.add(qh);
					}
				} else {
					history.add(qh);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}
		
	
	/**
	 * Check if user gets achievement for creating a certain number of quizzes
	 * @param user_id user ID 
	 * @return true if achivement added, false if not 
	 */
	public boolean authorAchievement(int user_id) {
		int count = 0;
		boolean achievementAdded = false;
		try {
			Statement selectStmt = con.createStatement();
			PreparedStatement insertStmt = null;
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes WHERE author_id = \"" + user_id + "\"");
			while(rs.next()){
				count++;
			}
			if (count == 1) {
				insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Amateur Author");
				achievementAdded = true;
			}
			if (count == 5) {
				insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Prolific Author");
				achievementAdded = true;
			}
			if (count == 10) {
				insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Prodigious Author");
				achievementAdded = true;
			}
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	/**
	 * Check if user gets achievement for taking a certain number of quizzes
	 * @param user_id user ID
	 * @return true if achivement added, false if not 
	 */
	public boolean quizTakerAchievement(int user_id) {
		int count = 0;
		boolean achievementAdded = false;
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = \"" + user_id + "\"");
			while(rs.next()){
				count++;
			}
			if (count == 10) {
				PreparedStatement insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Quiz Machine");
				insertStmt.executeUpdate();
				achievementAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	/**
	 * Method called only in practice mode. Adds achievement to user if user did not already have this achievement.
	 * @return added successfully
	 */
	public boolean practiceMakesPerfect(int user_id) {
		boolean achievementAdded = false; 
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\" AND description = Practice Makes Perfect");
			if (rs == null) {
				PreparedStatement insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Practice Makes Perfect");
				insertStmt.executeUpdate();
				achievementAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	/**
	 * Check if user has the highest score on a quiz
	 * @param user_id user ID
	 * @param quiz_id quiz ID 
	 * @param score user's score
	 * @return true if added, false otherwise
	 */
	public boolean iAmGreatestAchievement(int user_id, int quiz_id, int score) {
		int highest = 0;
		boolean alreadyAdded = false;
		boolean achievementAdded = false;
		String achievement = "I am the Greatest" + quiz_id;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\"");
			while(rs.next()) {
				if (rs.getString("description").equals(achievement)) alreadyAdded = true;
			}
			if (!alreadyAdded) {
				rs = stmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\"");
				while(rs.next()){
					int currScore = rs.getInt("score");
					if (currScore > highest) {
						highest = currScore;
					}
					if (score >= highest) {
						PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
						prepStmt.setInt(1, user_id);
						prepStmt.setString(2, achievement);
						achievementAdded = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	/**
	 * Get all of user's achievements
	 * @param user_id user ID
	 * @return arraylist of achievements
	 */
	public ArrayList<String> getAchievements(int user_id) {
		ArrayList<String> achievements = new ArrayList<String>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\"");
			while(rs.next()){
				achievements.add(rs.getString("description"));
			}
			return achievements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}

	
	
//	public int numQuizMade(int user_id) {
//	int count = 0;
//	Statement stmt;
//	try {
//		stmt = con.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE author_id = \"" + user_id + "\"");
//		while(rs.next()){
//			count++;
//		}
//		return count;
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return count;
//}

//public int numQuizzesTaken(int user_id) {
//	int count = 0;
//	Statement stmt;
//	try {
//		stmt = con.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = \"" + user_id + "\"");
//		while(rs.next()){
//			count++;
//		}
//		return count;
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return count;
//}
}
