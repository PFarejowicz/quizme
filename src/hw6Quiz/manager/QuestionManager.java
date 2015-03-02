package hw6Quiz.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuestionManager {

	private Connection con;

	public QuestionManager(Connection con) {
		this.con = con;
	}
	
	public void addTextResponseQuestion(int quiz_id, String prompt, String answer, int points) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO text_questions (quiz_id, prompt, response, points) VALUES(?, ?, ?, ?)");
			prepStmt.setInt(1, quiz_id);
			prepStmt.setString(2, prompt);
			prepStmt.setString(3, answer);
			prepStmt.setInt(4, points);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addFillBlankQuestion(String prompt) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO fill_blank_questions VALUES(?, ?, ?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addMultipleChoiceQuestion(String prompt) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO multiple_choice_questions VALUES(?, ?, ?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addPictureQuestion(String prompt) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO picture_questions VALUES(?, ?, ?, ?)");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
