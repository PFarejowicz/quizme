package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String question_text;
	private String responses_text;
	private String answer_text;

	public MultipleChoice(int quiz_id, int author_id, String prompt, String responses, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question_text = prompt;
		this.responses_text = responses;
		this.answer_text = answer;
	}
	
	public int getNumChoices() {
		return responses_text.split(",").length;
	}

	public String getQuestionText() {
		return question_text;
	}
	
	public ArrayList<String> getChoicesText() {
		ArrayList<String> choiceList = new ArrayList<String>(Arrays.asList(responses_text.split(",")));
		return choiceList;
	}
	
	public String getAnswerText() {
		return answer_text;
	}

}
