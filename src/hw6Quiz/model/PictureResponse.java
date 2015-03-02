package hw6Quiz.model;

public class PictureResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int quiz_id;
	private int author_id;
	private String img_url;
	private String answer_text;

	public PictureResponse(int quiz_id, int author_id, String url, String answer) {
		this.quiz_id = quiz_id;
		this.author_id = author_id;
		this.img_url = url;
		this.answer_text = answer;
	}

	public String getImageURL() {
		return img_url;
	}
	
	public String getAnswerText() {
		return answer_text;
	}

}
