package hw6Quiz;

import java.util.*;

public class User {

	String username = "";
	String password = "";
	ArrayList<String> message;
	ArrayList<String> friends;
	
	public User(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}
	
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	
}
