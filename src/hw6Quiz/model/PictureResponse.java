package hw6Quiz.model;

public class PictureResponse extends Question {

	private static final long serialVersionUID = 1L;

	public PictureResponse(int quiz_id, int author_id, String url, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = url;
		this.answer = answer;
	}
	
	public int getQuizID() {
		return quiz_id;
	}
	
	public String getQuestionText() {
		return question;
	}
	
	public String getAnswerText() {
		return answer.toLowerCase();
	}
}
