package singleton;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	
	/**
	 * For the database to work, you have to create it on localhost and change 
	 * the USUARIO, CONTRASENNA and jdbc_url constants,
	 * because we no longer have access to the database from the url
	 */
	
	private static final String CONTRASENNA = "5d0xhvhz";

	private static final String USUARIO = "USER_BD40";
	
	private static final String JDBC_URL = "jdbc:mysql://iescristobaldemonroy.duckdns.org:33306/BD40";
	
	private static Connection instance = null;
	
	private DBConnection() throws SQLException {
		
		instance = DriverManager.getConnection(JDBC_URL, USUARIO, CONTRASENNA);
		
	}
	
	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			new DBConnection();
		}
		
		return instance;
	}
	

}