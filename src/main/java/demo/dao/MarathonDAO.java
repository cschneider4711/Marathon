package demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.pojo.Marathon;

public class MarathonDAO {

	private Connection connection;

	public MarathonDAO(Connection connection) {
		super();
		this.connection = connection;
	}

	
	public List<Marathon> loadMarathons() throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery("SELECT id, title FROM marathon");
			final List<Marathon> marathons = new ArrayList<Marathon>();
			while (resultSet.next()) {
				Marathon marathon = new Marathon();
				marathon.setId( resultSet.getLong("id") );
				marathon.setTitle( resultSet.getString("title") );
				marathons.add(marathon);
			}
			return marathons;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}
	
	

	
}
