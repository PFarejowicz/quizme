package hw6Quiz.model;

public abstract class Question implements java.io.Serializable {
	protected static final long serialVersionUID = 1L;
	protected int quiz_id;
	protected int author_id;
	protected String question;
	protected String answer;
	
	abstract int getQuizID();
	abstract String getQuestionText();
}
