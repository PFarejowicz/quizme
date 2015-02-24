package hw6Quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	static String account = DBInfo.MYSQL_USERNAME;
	static String password = DBInfo.MYSQL_PASSWORD;
	static String server = DBInfo.MYSQL_DATABASE_SERVER;
	static String database = DBInfo.MYSQL_DATABASE_NAME;
	private Connection con;
	private Statement stmt;

	public DBConnection() {
		try {
			// open the connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
			
			// create a new Statement object
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the mySQL database Connection Object
	 */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * Close the connection to the database on exit
	 */
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}