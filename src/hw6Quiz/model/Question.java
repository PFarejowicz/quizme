package hw6Quiz.model;

public abstract class Question implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	protected int quiz_id;
	protected int author_id;
	protected String question;
	protected String answer;
	
	abstract String getQuestionText();
}
