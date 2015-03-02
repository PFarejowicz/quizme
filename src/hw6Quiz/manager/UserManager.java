package hw6Quiz.manager;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class UserManager {

	private Connection con; 
	private ResultSet rs; 
	
	public UserManager(Connection con) {
		this.con = con; 
	}

	public boolean containsUser(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			return (rs.next());				// not empty
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean isAdmin(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			if(rs.next()){
				return rs.getBoolean("admin_privilege");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public boolean checkPassword(String email, String password) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
			prepStmt.setString(1, email);										// fill in prepared statement
			rs = prepStmt.executeQuery();
			if (rs.next()) {													// validate email
				String salt = rs.getString("salt");
				MessageDigest md = MessageDigest.getInstance("SHA");
				password = password + salt; 									// adding salt to original password
				byte[] mdbytes = md.digest(password.getBytes());				// hash the password + salt 
				password = UserManager.hexToString(mdbytes);					// convert back to string
				return rs.getString("password").equals(password);				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	

	public void addUser(String email, String password, String name) {
		try {
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO users (email, password, salt, name, admin_privilege) VALUES(?, ?, ?, ?, ?)");
			prepStmt.setString(1, email);
			prepStmt.setString(4, name);
			
			final Random r = new SecureRandom();
			byte[] saltBytes = new byte[32];			
			r.nextBytes(saltBytes);											// generate salt
			String salt = UserManager.hexToString(saltBytes);
			prepStmt.setString(3, salt);
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			password = password + salt; 									// adding salt to original password
			byte[] mdbytes = md.digest(password.getBytes());				// hash the password + salt 
			password = UserManager.hexToString(mdbytes);					// convert back to string	
			prepStmt.setString(2, password);
			prepStmt.setBoolean(5, false);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getIDByEmail(String email) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \"" + email + "\"");
			if(rs.next()){
				return rs.getInt("user_id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getNameByID(int user_id) {
		Statement stmt; 
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = \"" + user_id + "\"");
			if(rs.next()){
				return rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
