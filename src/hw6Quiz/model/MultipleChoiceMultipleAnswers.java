package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceMultipleAnswers extends Question {

	private static final long serialVersionUID = 1L;
	private String choices;

	public MultipleChoiceMultipleAnswers(int quiz_id, int author_id, String prompt, String choices, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.choices = choices;
		this.answer = answer;
	}
	
	public int getQuizID() {
		return quiz_id;
	}
	
	public int getNumChoices() {
		return choices.split(",").length;
	}
	
	public int getNumAnswers() {
		return getAnswerAsList().size();
	}

	public String getQuestionText() {
		return question;
	}
	
	public String getChoicesAsText() {
		return choices;
	}
	
	public ArrayList<String> getChoicesAsList() {
		ArrayList<String> choiceList = new ArrayList<String>(Arrays.asList(choices.trim().toLowerCase().split("\\s*,\\s*")));
		return choiceList;
	}
	
	public ArrayList<String> getAnswerAsList() {
		ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(answer.trim().toLowerCase().split("\\s*,\\s*")));
		return answerList;
	}
}
