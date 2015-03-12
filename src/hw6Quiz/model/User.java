package hw6Quiz.model;

public class User {

	private int id;
	private String email;
	private String name;
	private boolean admin;
	
	public User(int id, String email, String name, boolean admin) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.admin = admin;
	}
	
	public int getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean getAdmin(){
		return admin;
	}
}
