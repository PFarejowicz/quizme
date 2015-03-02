package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class FillInTheBlank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String question_text;
	private String answer_text;

	public FillInTheBlank(int quiz_id, int author_id, String prompt, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question_text = prompt;
		this.answer_text = answer;
	}
	
	public int getNumBlanks() {
		return answer_text.split("\\*").length;
	}

	public String getQuestionText() {
		return question_text.replaceAll("\\*", "__________");
	}
	
	public ArrayList<String> getAnswerText() {
		ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(answer_text.split(",")));
		return answerList;
	}

}
