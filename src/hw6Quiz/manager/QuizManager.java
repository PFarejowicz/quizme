package hw6Quiz.manager;


import hw6Quiz.model.*;

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
	public void addQuiz(String name, String description, int author, String category, String tags, boolean random, boolean pages, boolean correction, int points) {
		try {
		    Calendar calendar = Calendar.getInstance();
		    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
		    tags = tags.replace(" ", "");
			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO quizzes (name, description, author_id, category, tags, random_order, multiple_pages, immediate_correction, date_time, points, reported) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertStmt.setString(1, name);
			insertStmt.setString(2, description);
			insertStmt.setInt(3, author);
			insertStmt.setString(4, category);
			insertStmt.setString(5, tags);
			insertStmt.setBoolean(6, random);
			insertStmt.setBoolean(7, pages);
			insertStmt.setBoolean(8, correction);
			insertStmt.setTimestamp(9, timeStamp);
			insertStmt.setInt(10, points);
			insertStmt.setBoolean(11, false);
			insertStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuiz(String name, String description, String category, String tags, boolean random, boolean pages, boolean correction, int quiz_id) {
		try {
			tags = tags.replace(" ", "");
			PreparedStatement updateStmt = con.prepareStatement("UPDATE quizzes SET name = ?, description = ?, category = ?, tags = ?, random_order = ?, multiple_pages = ?, immediate_correction = ? WHERE quiz_id = ?");
			updateStmt.setString(1, name);
			updateStmt.setString(2, description);
			updateStmt.setString(3, category);
			updateStmt.setString(4, tags);
			updateStmt.setBoolean(5, random);
			updateStmt.setBoolean(6, pages);
			updateStmt.setBoolean(7, correction);
			updateStmt.setInt(8, quiz_id);
			updateStmt.executeUpdate();
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
			return rs.getInt("quiz_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getQuizNameByID(int id) {
		Statement stmt;
		String name = "";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE quiz_id = \"" + id + "\"");
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return name;
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
				Quiz quiz = new Quiz(quiz_id, rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
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
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				quizList.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	public ArrayList<Quiz> searchQuizzes(String name, String category, String tag) {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		StringBuilder result = new StringBuilder("SELECT * FROM quizzes");
		if (!name.equals("") || !category.equals("") || !tag.equals("")) result.append(" WHERE"); 
		if (!name.equals("")) {
            result.append(" name LIKE \"%" + name + "%\"");
            if (!category.equals("") || !tag.equals("")) result.append(" AND");
        }
        if (!category.equals("")) {
            result.append(" category LIKE \"%" + category + "%\"");
            if (!tag.equals("")) result.append(" AND");
        }
        if (!tag.equals("")) {
        	result.append(" tags LIKE \"%" + tag + "%\"");
        }
		try {
			ResultSet rs = con.createStatement().executeQuery(result.toString());
			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				quizList.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	public int numTimesTaken(int quiz_id) {
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\"");
			rs.last(); 
			return rs.getRow(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double avgQuizScore(int quiz_id) {
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\"");
			if (rs.next()) {
				double sum = rs.getInt("score");
				while (rs.next()) {
					sum += rs.getInt("score");
				}
				int count = numTimesTaken(quiz_id);
				return sum / (double) count; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int quizRange(int quiz_id) {
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\"");
			if (rs.next()) {
				int high = rs.getInt("score");
				int low = high;
				while (rs.next()) {
					int sc = rs.getInt("score");
					if (sc > high) {
						high = sc; 
					}
					if (sc < low) {
						low = sc; 
					}
				}
				return high - low; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<Quiz> getPopularQuizzes(){
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		int [] topFrequencies = new int[3];
		Map<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history");
			while (rs.next()) {
				int quizId = rs.getInt("quiz_id");
				if (frequencies.containsKey(quizId)) {
					frequencies.put(quizId, frequencies.get(quizId) + 1);
				} else {
					frequencies.put(quizId, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int key: frequencies.keySet()){
			Quiz quiz = getQuizByID(key);
			if(frequencies.get(key) > topFrequencies[0]){
				topFrequencies[2] = topFrequencies[1];
				topFrequencies[1] = topFrequencies[0];
				topFrequencies[0] = frequencies.get(key);
				quizList.add(0, quiz);
			} else if(frequencies.get(key) > topFrequencies[1]){
				topFrequencies[2] = topFrequencies[1];
				topFrequencies[1] = frequencies.get(key);
				quizList.add(1, quiz);
			} else if(frequencies.get(key) > topFrequencies[2]){
				topFrequencies[2] = frequencies.get(key);
				quizList.add(2, quiz);
			}
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
	public String convertToPercStr(double score, double total) {
		return String.format("%.2f", (float) score / (float) total * 100) + "%";
	}
	
	/**
	 * Calculates average rating given a quiz_id
	 * @param quiz_id quiz ID
	 * @return average rating
	 */
	public double calculateRating(int quiz_id) {
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\"");
			if (rs.next()) {
				double sum = rs.getDouble("rating");
				double count = 1;
				while (rs.next()) {
					sum += rs.getDouble("rating");
					count++;
				}
				return sum / count;  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Adds quiz result to quiz history
	 * @param quiz_id quiz ID
	 * @param user_id user ID
	 * @param score score of quiz
	 */
	public void addQuizResult(int quiz_id, int user_id, int score, int total, int rating, String review, String name, Timestamp time_taken) {
		try {
			Calendar calendar = Calendar.getInstance();
		    Timestamp timeStamp = new Timestamp(calendar.getTime().getTime());
			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO quiz_history (quiz_id, user_id, score, total, rating, review, name, date_time, time_taken) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertStmt.setInt(1, quiz_id);
			insertStmt.setInt(2, user_id);
			insertStmt.setInt(3, score);
			insertStmt.setInt(4, total);
			insertStmt.setInt(5, rating);
			insertStmt.setString(6, review);
			insertStmt.setString(7, name);
			insertStmt.setTimestamp(8, timeStamp);
			insertStmt.setTimestamp(9, time_taken);
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
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" AND user_id = \"" + user_id + "\" ORDER BY date_time DESC, time_taken ASC");
			} else if (type.equals("all time high") || type.equals("last day high")) {
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" ORDER BY score DESC, time_taken ASC");
			} else if (type.equals("recent")) {
				rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \"" + quiz_id + "\" ORDER BY date_time DESC, time_taken ASC");
			} else if (type.equals("reviews")) {
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
	 * Checks if a quiz is reported or not.
	 * @param quiz_id
	 * @return
	 */
	public boolean checkQuizReported(int quiz_id){
		try{
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes WHERE quiz_id = " + quiz_id);
			if(rs.next()){
				return rs.getBoolean("reported");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Reports a quiz
	 * @param quiz_id
	 */
	public void reportQuiz(int quiz_id){
		try{
			PreparedStatement updateStmt = con.prepareStatement("UPDATE quizzes SET reported = 1 WHERE quiz_id = ?");
			updateStmt.setInt(1, quiz_id);
			updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Unreports a quiz
	 * @param quiz_id
	 */
	public void unreportQuiz(int quiz_id){
		try{
			PreparedStatement updateStmt = con.prepareStatement("UPDATE quizzes SET reported = 0 WHERE quiz_id = ?");
			updateStmt.setInt(1, quiz_id);
			updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if user gets achievement for creating a certain number of quizzes
	 * @param user_id user ID 
	 * @return true if achievement added, false if not 
	 */
	public boolean authorAchievement(int user_id) {
		boolean achievementAdded = false;
		boolean amateur = false;
		boolean prolific = false;
		boolean prodigious = false;
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes WHERE author_id = \"" + user_id + "\"");
			rs.last(); 
			int count = rs.getRow(); 
			rs = selectStmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\"");
			if (rs.next()) {
				if (rs.getString("description").equals("Amateur Author")) amateur = true;
				if (rs.getString("description").equals("Prolific Author")) prolific = true;
				if (rs.getString("description").equals("Prodigious Author")) prodigious = true;
			}
			if (count == 1 && !amateur) {
				PreparedStatement insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Amateur Author");
				insertStmt.executeUpdate();
				achievementAdded = true;
			}
			if (count == 5 && !prolific) {
				PreparedStatement insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Prolific Author");
				insertStmt.executeUpdate();
				achievementAdded = true;
			}
			if (count == 10 && !prodigious) {
				PreparedStatement insertStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStmt.setInt(1, user_id);
				insertStmt.setString(2, "Prodigious Author");
				insertStmt.executeUpdate();
				achievementAdded = true;
			}
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
		boolean achievementAdded = false;
		boolean quizMachine = false;
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quiz_history WHERE user_id = \"" + user_id + "\"");
			rs.last(); 
			int count = rs.getRow(); 
			rs = selectStmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\"");
			if (rs.next()) {
				if (rs.getString("description").equals("Quiz Machine")) quizMachine = true;
			}
			if (count == 10 && !quizMachine) {
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
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM achievements WHERE user_id = \"" + user_id + "\" AND description = \"Practice Makes Perfect\"");
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
				}
				if (score >= highest) {
					PreparedStatement prepStmt = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
					prepStmt.setInt(1, user_id);
					prepStmt.setString(2, achievement);
					prepStmt.executeUpdate();
					achievementAdded = true;
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
			while (rs.next()) {
				achievements.add(rs.getString("description"));
			}
			return achievements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}

	public ArrayList<Quiz> getMostRecentlyCreatedQuizzes(){
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz>();
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes ORDER BY date_time DESC");
			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				recentQuizzes.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
	}
	
	public boolean quizExists(int quiz_id) {
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes WHERE quiz_id = \"" + quiz_id + "\"");
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
