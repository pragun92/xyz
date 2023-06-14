package application.utils;

import java.sql.*;

public class DatabaseConnection {
	
	private static DatabaseConnection databaseConnection = null;
	
	private DatabaseConnection() {

	}

	public static DatabaseConnection getInstance() {
		if (databaseConnection == null) {
			databaseConnection = new DatabaseConnection();
		}
		return databaseConnection;
	}
	
	public Connection connect() {
		Connection conn = null;
		try{
			// connection
			String url = "jdbc:mysql://localhost:3306/xyz";
			String user = "root";
			String password = "";

			conn = DriverManager.getConnection(url, user, password);
			if (conn != null) {
				System.out.println("Connected to the database xyz");
			}else {
				
			}
		}catch(SQLException ex){
			System.out.println("Exception ::" + ex.getMessage());
			ex.printStackTrace();
		}
		
		return conn;
	}
	
	
	public static void main(String[] args) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		connection.connect();
	}

}
