package hw6Quiz.manager;

import hw6Quiz.model.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class QuizManager {
	
	private Connection con; 

	public QuizManager(Connection con) {
		this.con = con; 
	}
	
	public void addQuiz(String name, String description, int author, boolean random, boolean pages, boolean correction, int points) {
		try {
		    Calendar calendar = Calendar.getInstance();
		    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO quizzes (name, description, author_id, random_order, multiple_pages, immediate_correction, date_time, points) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			prepStmt.setString(1, name);
			prepStmt.setString(2, description);
			prepStmt.setInt(3, author);
			prepStmt.setBoolean(4, random);
			prepStmt.setBoolean(5, pages);
			prepStmt.setBoolean(6, correction);
			prepStmt.setTimestamp(7, timeStamp);
			prepStmt.setInt(8, points);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getIDByName(String name) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM quizzes WHERE name = ?");
			prepStmt.setString(1, name);
			ResultSet rs = prepStmt.executeQuery();
			rs.next();
			System.out.println(rs.getInt("quiz_id"));
			return rs.getInt("quiz_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
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
	
	public void updateQuizPoints(int quiz_id, int points) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("UPDATE quizzes SET points = ? WHERE quiz_id = ?");
		    prepStmt.setInt(1, points);
		    prepStmt.setInt(2, quiz_id);
		    prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getQuizPoints(int quiz_id) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM quizzes WHERE quiz_id = ?");
			prepStmt.setInt(1, quiz_id);
			ResultSet rs = prepStmt.executeQuery();
			rs.next();
			return rs.getInt("points");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Adds quiz result to quiz history
	 * @param quiz_id quiz ID
	 * @param user_id user ID
	 * @param score score of quiz
	 */
	public void addQuizResult(int quiz_id, int user_id, int score, int rating, String review, String name) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO quiz_history (quiz_id, user_id, score, rating, review, name) VALUES (?, ?, ?, ?, ?, ?)");
			prepStmt.setInt(1, quiz_id);
			prepStmt.setInt(2, user_id);
			prepStmt.setInt(3, score);
			prepStmt.setInt(4, rating);
			prepStmt.setString(5, review);
			prepStmt.setString(6, name);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	public boolean authorAchievement(int user_id) {
		int count = 0;
		Statement stmt;
		boolean achievementAdded = false;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE author_id = \"" + user_id + "\"");
			while(rs.next()){
				count++;
			}
			if (count == 1) {
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				prepStmt.setInt(1, user_id);
				prepStmt.setString(2, "Amateur Author");
				achievementAdded = true;
			}
			if (count == 5) {
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				prepStmt.setInt(1, user_id);
				prepStmt.setString(2, "Prolific Author");
				achievementAdded = true;
			}
			if (count == 10) {
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				prepStmt.setInt(1, user_id);
				prepStmt.setString(2, "Prodigious Author");
				achievementAdded = true;
			}
			return achievementAdded;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	public boolean quizTakerAchievement(int user_id) {
		int count = 0;
		Statement stmt;
		boolean achievementAdded = false;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = \"" + user_id + "\"");
			while(rs.next()){
				count++;
			}
			if (count == 10) {
				PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				prepStmt.setInt(1, user_id);
				prepStmt.setString(2, "Quiz Machine");
				achievementAdded = true;
			}
			return achievementAdded;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	public boolean iAmGreatestAchievement(int user_id, int quiz_id, int score) {
		int highest = 0;
		Statement stmt;
		boolean alreadyAdded = false;
		boolean achievementAdded = false;
		String achievement = "I am the Greatest" + quiz_id;
		try {
			stmt = con.createStatement();
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
				return achievementAdded;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
//	public int numQuizMade(int user_id) {
//		int count = 0;
//		Statement stmt;
//		try {
//			stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE author_id = \"" + user_id + "\"");
//			while(rs.next()){
//				count++;
//			}
//			return count;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return count;
//	}
	
//	public int numQuizzesTaken(int user_id) {
//		int count = 0;
//		Statement stmt;
//		try {
//			stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = \"" + user_id + "\"");
//			while(rs.next()){
//				count++;
//			}
//			return count;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return count;
//	}
	
	public ArrayList<String> getAchievements(int user_id) {
		ArrayList<String> achievements = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\"");
			while(rs.next()){
				achievements.add(rs.getString("description"));
			}
			return achievements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}

}
