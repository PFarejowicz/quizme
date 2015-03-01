package hw6Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuizManager {
	
	private Connection con; 
	private ResultSet rs;

	public QuizManager(Connection con) {
		this.con = con; 
	}
	
	public void addQuiz() {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO quizzes VALUES(?, ?, ?, ?, ?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
