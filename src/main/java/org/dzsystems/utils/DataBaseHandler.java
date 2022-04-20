package org.dzsystems.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHandler {

	private static Connection connection;
	private static String url;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				File dbfile = new File("");
				url = "jdbc:sqlite:" + dbfile.getAbsolutePath() + "/test.db";
				connection = DriverManager.getConnection(url);
			} catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Connection to database is closed.");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void initialise() {
		Statement statement;
		try {
			getConnection();
			statement = connection.createStatement();
			statement.execute(
					"CREATE TABLE IF NOT EXISTS wells ("
							+ "id INTEGER PRIMARY KEY AUTOINCREMENT ,"
							+ "name VARCHAR (32) UNIQUE)");
			
			statement.execute(
					"CREATE TABLE IF NOT EXISTS equipment ("
							+ "id INTEGER PRIMARY KEY AUTOINCREMENT ,"
							+ "name VARCHAR (32) UNIQUE, "
							+ "well_id INTEGER ,"
							+ "FOREIGN KEY (well_id) REFERENCES well (id))");
			System.out.println("Connection to database is established. Tables are created.");
			System.out.println("Database file: " + url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
}
