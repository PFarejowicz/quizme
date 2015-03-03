package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String question;
	private String choices;
	private String answer;

	public MultipleChoice(int quiz_id, int author_id, String prompt, String choices, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.choices = choices;
		this.answer = answer;
	}
	
	public int getNumChoices() {
		return choices.split(",").length;
	}

	public String getQuestionText() {
		return question;
	}
	
	public ArrayList<String> getChoicesText() {
		ArrayList<String> choiceList = new ArrayList<String>(Arrays.asList(choices.split(",")));
		return choiceList;
	}
	
	public String getAnswerText() {
		return answer;
	}

}
