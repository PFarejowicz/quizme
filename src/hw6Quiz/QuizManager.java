package hw6Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	
	public int getId(String name) {
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

}
