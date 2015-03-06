package hw6Quiz.manager;

import hw6Quiz.model.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizManager {
	
	private Connection con; 

	public QuizManager(Connection con) {
		this.con = con; 
	}
	
	public void addQuiz(String name, String description, int author, boolean random, boolean pages, boolean correction) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO quizzes (name, description, author_id, random_order, multiple_pages, immediate_correction) VALUES(?, ?, ?, ?, ?, ?)");
			prepStmt.setString(1, name);
			prepStmt.setString(2, description);
			prepStmt.setInt(3, author);
			prepStmt.setBoolean(4, random);
			prepStmt.setBoolean(5, pages);
			prepStmt.setBoolean(6, correction);
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
				Quiz quiz = new Quiz(quiz_id, rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"));
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
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"));
				quizList.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	/**
	 * Adds quiz result to quiz history
	 * @param quiz_id quiz ID
	 * @param user_id user ID
	 * @param score score of quiz
	 */
	public void addQuizResult(int quiz_id, int user_id, int score) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO quiz_history (quiz_id, user_id, score) VALUES (?, ?, ?)");
			prepStmt.setInt(1, quiz_id);
			prepStmt.setInt(2, user_id);
			prepStmt.setInt(3, score);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addRatingAndReview(int quiz_id, int rating, String review) {
		try {
			Statement stmt = con.createStatement();
			String updateRating = "UPDATE quiz_history SET rating = " + rating + "WHERE quiz_id = " + quiz_id;
			stmt.executeUpdate(updateRating);
			String updateReview = "UPDATE quiz_history SET review = " + review + "WHERE quiz_id = " + quiz_id;
			stmt.executeUpdate(updateReview);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
