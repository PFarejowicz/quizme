package hw6Quiz.model;

public class QuestionResponse extends Question {

	public QuestionResponse(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
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
