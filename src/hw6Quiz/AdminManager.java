package hw6Quiz;

import java.sql.Connection;
import java.sql.ResultSet;

public class AdminManager {
	
	private Connection con; 
	private ResultSet rs;

	public AdminManager(Connection con) {
		this.con = con;
		// TODO Auto-generated constructor stub
	}
	
	public void addAnnouncement(String announcement){
		
	}
	
	public void removeAnnouncement(int id){
		
	}
	
	public void removeUserAccount(int id){
		
	}
	
	public void clearHistoryForQuiz(int id){
		
	}
	
	public void promoteToAdmin(int id){
		
	}
	
	public int getNumberOfUsers(){
		return 0; // TODO
	}
	
	public int getNumberOfQuizzes(){
		return 0; // TODO
	}

}
