package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class FillInTheBlank extends Question {

	private static final long serialVersionUID = 1L;

	public FillInTheBlank(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.answer = answer;
	}
	
	public int getQuizID() {
		return quiz_id;
	}
	
	public int getNumBlanks() {
		int count = 1;
		count += answer.length() - answer.replace(",", "").length();
		return count;
	}
	
	public String getQuestionText() {
		return question.replaceAll("\\*", "__________");
	}
	
	public String getQuestionRegex() {
		return question;
	}
	
	public String getAnswerAsText() {
		return answer;
	}
	
	public ArrayList<String> getAnswerAsList() {
		ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(answer.trim().toLowerCase().split("\\s*,\\s*")));
		return answerList;
	}

}
