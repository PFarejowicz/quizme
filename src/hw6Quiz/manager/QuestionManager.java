package hw6Quiz.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuestionManager {

	private Connection con;

	public QuestionManager(Connection con) {
		this.con = con;
	}
	
	public void addQuestion(int quiz_id, String prompt, String answer, int points) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO questions (quiz_id, prompt, response, points) VALUES(?, ?, ?, ?)");
			prepStmt.setInt(1, quiz_id);
			prepStmt.setString(2, prompt);
			prepStmt.setString(3, answer);
			prepStmt.setInt(4, points);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
