package hw6Quiz.model;
import java.sql.*;

public class QuizHistory {
	
	private int quizHistoryId;
	private int quizId;
	private int userId;
	private int score;
	private int total;
	private int rating;
	private String review;
	private String name;
	private Timestamp dt;

	public QuizHistory(int quizHistoryId, int quizId, int userId, int score, int total, int rating, String review, String name, Timestamp dt) {
		this.quizHistoryId = quizHistoryId;
		this.quizId = quizId;
		this.userId = userId;
		this.score = score;
		this.total = total;
		this.rating = rating;
		this.review = review;
		this.name = name;
		this.dt = dt;
	}
	
	public int getQuizHistoryId(){
		return quizHistoryId;
	}
	
	public int getQuizId(){
		return quizId;
	}
	
	public int userId(){
		return userId;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getTotal(){
		return total;
	}
	
	public int getRating(){
		return rating;
	}
	
	public String getReview(){
		return review;
	}

	public String getName(){
		return name;
	}
	
	public Timestamp getTimeStamp(){
		return dt;
	}
}
