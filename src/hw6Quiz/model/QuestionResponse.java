package hw6Quiz.model;

public class QuestionResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String question;
	private String answer;

	public QuestionResponse(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.answer = answer;
	}

	public String getQuestionText() {
		return question;
	}
	
	public String getAnswerText() {
		return answer;
	}
}
