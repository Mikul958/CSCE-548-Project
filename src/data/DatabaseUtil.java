package data;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil
{
    private static final String URL = "jdbc:mysql://localhost:3306/speedrun_app";
	private static final String USER = "speedrun_db";
	private static final String PASS = "notmyrootpassword321";

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
