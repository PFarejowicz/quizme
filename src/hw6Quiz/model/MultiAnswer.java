package hw6Quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiAnswer extends Question {
	private static final long serialVersionUID = 1L;
	private boolean in_order;
	private int num_correct;

	public MultiAnswer(int quiz_id, int author_id, String prompt, String answer, int num_correct, boolean inOrder) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.question = prompt;
		this.answer = answer;
		this.in_order = inOrder;
		this.num_correct = num_correct;
	}
	
	public int getQuizID() {
		return quiz_id;
	}

	public String getQuestionText() {
		return question;
	}
	
	public String getAnswerAsText() {
		return answer;
	}
	
	public ArrayList<String> getAnswerAsList() {
		ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(answer.trim().toLowerCase().split("\\s*,\\s*")));
		return answerList;
	}
	
	public boolean getInOrder() {
		return in_order;
	}

	public int getNumAnswers() {
		return num_correct;
	}
}
