package hw6Quiz.model;

public class Challenge {
	int sender_id;
	int receiver_id;
	int quiz_id;
	int score;
	
	public Challenge (int sender_id, int receiver_id, int quiz_id, int score) {
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.quiz_id = quiz_id;
		this.score = score;
	}
	
	public int getSenderId() {
		return sender_id;
	}
	
	public int getReceiverId() {
		return receiver_id;
	}
	
	public int getQuizId() {
		return quiz_id;
	}
	
	public int getScore() {
		return score;
	}
}
