package demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SystemDAO {

	private Connection connection;

	public SystemDAO(Connection connection) {
		super();
		this.connection = connection;
	}


	public void createAccount(String username, String password) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("INSERT INTO app_user (username, password) VALUES (?, ?)");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.executeUpdate();
			
			statement = this.connection.prepareStatement("INSERT INTO app_role (username, rolename) VALUES (?, ?)");
			statement.setString(1, username);
			statement.setString(2, "runner"); // use runner role here
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}


	public void changePassword(String username, String password) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("UPDATE app_user SET password = ? WHERE username = ?");
			statement.setString(1, password);
			statement.setString(2, username);
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}
}
