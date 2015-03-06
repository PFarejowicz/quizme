package hw6Quiz.model;

import java.sql.Timestamp;

public class Quiz {
	
	private int quiz_id;
	private String name;
	private String description;
	private int author_id;
	private boolean random_order;
	private boolean multiple_pages;
	private boolean immediate_correction;
	private Timestamp date_created;

	public Quiz(int quiz_id, String name, String description, int author_id, boolean randO, boolean multP, boolean immC, Timestamp dt) {
		this.quiz_id = quiz_id;
		this.name = name;
		this.description = description;
		this.author_id = author_id;
		this.random_order = randO;
		this.multiple_pages = multP;
		this.immediate_correction = immC;
		this.date_created = dt;
	}
	
	public int getQuizID() {
		return quiz_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getAuthorID() {
		return author_id;
	}
	
	public boolean isRandomOrder() {
		return random_order;
	}
	
	public boolean isMultiplePages() {
		return multiple_pages;
	}
	
	public boolean isImmediateCorrection() {
		return immediate_correction;
	}
	
	public Timestamp getDateAndTime() {
		return date_created;
	}

}
