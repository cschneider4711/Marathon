package demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo.pojo.Runner;

public class RunnerDAO {

	private Connection connection;

	public RunnerDAO(Connection connection) {
		super();
		this.connection = connection;
	}


	public Runner loadRunner(long runnerId) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT id, username, firstname, lastname, street, zip, city, date_of_birth, creditcard_number, photo_name FROM runner WHERE id = ?");
			statement.setLong(1, runnerId);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return extractRunner(resultSet,"",true);
			}
			return null;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}


	public Runner loadRunnerByName(String runnerName) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT id, username, firstname, lastname, street, zip, "
					+"city, date_of_birth, creditcard_number, photo_name FROM runner WHERE username = ?");
			statement.setString(1, runnerName);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return extractRunner(resultSet,"", true);
			}
			return null;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}
	
	public List<Runner> searchRunners(String searchTerm) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			searchTerm = searchTerm.trim();
			if (searchTerm.length() < 4) {
				throw new SQLException("Not allowing a wide search. Please refine your search criteria");
			}
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery("SELECT id, username, firstname, lastname, street, zip, city, date_of_birth, photo_name FROM runner " +
					"WHERE username LIKE '%"+searchTerm+"%' " +
					" OR UPPER(firstname) LIKE '%"+searchTerm.toUpperCase()+"%' " +
					" OR UPPER(lastname) LIKE '%"+searchTerm.toUpperCase()+"%' ");
			final List<Runner> runners = new ArrayList<Runner>();
			while (resultSet.next()) {
				runners.add( extractRunner(resultSet,"",false) );
			}
			return runners;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}

	protected static Runner extractRunner(ResultSet resultSet, String prefix, boolean includeCreditcardNumber) throws SQLException {
		Runner runner = new Runner();
		runner.setId( resultSet.getLong(prefix+"id") );
		runner.setUsername( resultSet.getString(prefix+"username") );
		runner.setFirstname( resultSet.getString(prefix+"firstname") );
		runner.setLastname( resultSet.getString(prefix+"lastname") );
		runner.setStreet( resultSet.getString(prefix+"street") );
		runner.setZip( resultSet.getString(prefix+"zip") );
		runner.setCity( resultSet.getString(prefix+"city") );
		runner.setDateOfBirth( resultSet.getDate(prefix+"date_of_birth") );
		runner.setPhotoName( resultSet.getString(prefix+"photo_name") );
		if (includeCreditcardNumber) {
			runner.setCreditCardNumber( resultSet.getString(prefix+"creditcard_number") );
		}
		return runner;
	}



	public boolean hasRunnerFinished(String runner) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery("SELECT count(marathon_id) FROM run WHERE finishing_seconds IS NOT NULL AND runner_id = "+runner);
			if (resultSet.next()) {
				return resultSet.getInt(1) > 0;
			}
			return false;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}
	

	public void createRunner(String username, String firstname,
			String lastname, String street, String zip, String city,
			String creditcardNumber, String dateOfBirth) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("INSERT INTO runner (username, firstname, " +
					"lastname, street, zip, city, creditcard_number, date_of_birth) " +
					" VALUES (?, ?, ?, ?, ?, ?, ?, '" +
					DAOUtils.getDateForHSQLDB(dateOfBirth)+"')");
			statement.setString(1, username);
			statement.setString(2, firstname);
			statement.setString(3, lastname);
			statement.setString(4, street);
			statement.setString(5, zip);
			statement.setString(6, city);
			statement.setString(7, creditcardNumber);
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}


	public void updateRunner(Runner runner) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("UPDATE runner SET firstname=?, " +
					"lastname=?, street=?, zip=?, city=?, " +
					"creditcard_number=?, date_of_birth=? " +
					"WHERE id=? ");
			statement.setString(1, runner.getFirstname());
			statement.setString(2, runner.getLastname());
			statement.setString(3, runner.getStreet());
			statement.setString(4, runner.getZip());
			statement.setString(5, runner.getCity());
			statement.setString(6, runner.getCreditCardNumber());
			statement.setDate(7, new java.sql.Date(runner.getDateOfBirth().getTime()));
			statement.setLong(8, runner.getId());
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}
	
	
	public void updatePhoto(Runner runner) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("UPDATE runner " +
					"SET photo_name=? " +
					"WHERE id=? ");
			statement.setString(1, runner.getPhotoName());
			statement.setLong(2, runner.getId());
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}

	public List<Runner> getAllRunners() throws SQLException {
		List<Runner> runners = new ArrayList<>();

		PreparedStatement stmt = this.connection.prepareStatement("SELECT id, username, firstname, lastname, street, zip, "
				+"city, date_of_birth, creditcard_number, photo_name FROM runner");
		ResultSet rs = stmt.executeQuery();

		while(rs.next()) {
			Runner runner = extractRunner(rs, "", true);
			runners.add(runner);
		}

		return runners;
	}

	public List<Runner> getRunnersNotRegisteredOnAnyDiscipline() throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Runner> runners = new ArrayList<>();

		try {
			String sql = "SELECT runner.id, runner.username, runner.firstname, runner.lastname, runner.street, runner.zip, runner.city, runner.date_of_birth, runner.creditcard_number, runner.photo_name FROM runner WHERE runner.id NOT IN " +
					"(SELECT DISTINCT run.runner_id FROM run)";
			statement = this.connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Runner runner = extractRunner(resultSet, "", false);
				runners.add(runner);
			}
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
		return runners;
	}

}
