package hw6Quiz.model;

public class QuestionResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String question_text;
	private String answer_text;

	public QuestionResponse(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question_text = prompt;
		this.answer_text = answer;
	}

	public String getQuestionText() {
		return question_text;
	}
	
	public String getAnswerText() {
		return answer_text;
	}
}
