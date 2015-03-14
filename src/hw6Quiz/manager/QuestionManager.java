package hw6Quiz.manager;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class QuestionManager {

	private Connection con;

	public QuestionManager(Connection con) {
		this.con = con;
	}
	
	public void addQuestion(int quiz_id, String type, Object obj) {
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			os.close();
			
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO questions (quiz_id, question_type, metadata) VALUES(?, ?, ?)");
			prepStmt.setInt(1, quiz_id);
			prepStmt.setString(2, type);
			prepStmt.setObject(3, bs.toByteArray());
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuestion(int question_id, Object obj) {
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			os.close();
			
			PreparedStatement updateStmt = con.prepareStatement("UPDATE questions SET metadata = ? WHERE question_id = ?");
			updateStmt.setObject(1, bs.toByteArray());
			updateStmt.setInt(2, question_id);
			updateStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getQuestionIDs(int quiz_id) {
		ArrayList<Integer> questions = new ArrayList<Integer>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE quiz_id = \"" + quiz_id + "\"");
			while (rs.next()) {
				questions.add(rs.getInt("question_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("quiz id " + quiz_id);
		System.out.println("Empty ? " + questions.isEmpty());
		return questions;
	}
	
	public Object getQuestionByID(int question_id) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE question_id = \"" + question_id + "\"");
			if (rs.next()){
				Object obj = rs.getObject("metadata");
				ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) obj);
				ObjectInputStream is = new ObjectInputStream(bs);
				return is.readObject();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getTypeByID(int question_id) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE question_id = \"" + question_id + "\"");
			if (rs.next()){
				return rs.getString("question_type");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
