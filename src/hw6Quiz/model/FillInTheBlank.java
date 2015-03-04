package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class FillInTheBlank extends Question implements java.io.Serializable {

	public FillInTheBlank(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.answer = answer;
	}
	
	public int getNumBlanks() {
		int count = 1;
		count += answer.length() - answer.replace(",", "").length();
		return count;
	}
	
	public String getQuestionText() {
		return question.replaceAll("\\*", "__________");
	}
	
	public ArrayList<String> getAnswerText() {
		ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(answer.split(",")));
		return answerList;
	}

}
