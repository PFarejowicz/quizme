package hw6Quiz.model;

public class QuizHistory {
	
	private int quizHistoryId;
	private int quizId;
	private int userId;
	private int score;

	public QuizHistory(int quizHistoryId, int quizId, int userId, int score) {
		this.quizHistoryId = quizHistoryId;
		this.quizId = quizId;
		this.userId = userId;
		this.score = score;
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

}
