package hw6Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuestionManager {

	private Connection con;

	public QuestionManager(Connection con) {
		this.con = con;
	}
	
	public void addTextResponseQuestion(String prompt, int points) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO text_questions VALUES(?, ?, ?, ?)");
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
