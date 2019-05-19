package demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import demo.pojo.Marathon;
import demo.pojo.Results;
import demo.pojo.Run;

public class ResultsDAO {

	private Connection connection;

	public ResultsDAO(Connection connection) {
		super();
		this.connection = connection;
	}

	
	public Results loadResults(String marathonId, boolean includePending) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.createStatement();
			final String finishedFilter = includePending ? "" : " AND run.finishing_seconds IS NOT null ";
			resultSet = statement.executeQuery("SELECT marathon.id AS marathon_id, "+
							"  marathon.title AS marathon_title, "+
							"  run.finishing_seconds AS finishing_seconds, "+
							"  runner.id AS runner_id, "+
							"  runner.username AS runner_username, "+
							"  runner.firstname AS runner_firstname, "+
							"  runner.lastname AS runner_lastname, "+
							"  runner.street AS runner_street, "+
							"  runner.zip AS runner_zip, "+
							"  runner.city AS runner_city, "+
							"  runner.date_of_birth AS runner_date_of_birth," +
							"  runner.photo_name AS runner_photo_name "+
							"FROM marathon marathon, run run, runner runner "+
							"WHERE marathon.id = "+marathonId+
							"  AND marathon.id = run.marathon_id "+
							"  AND runner.id = run.runner_id "+
							finishedFilter+
							"ORDER BY run.finishing_seconds");
			final Results results = new Results();
			final List<Run> runs = new ArrayList<Run>();
			while (resultSet.next()) {
				if (results.getMarathon() == null) {
					Marathon marathon = new Marathon( resultSet.getLong("marathon_id"), resultSet.getString("marathon_title") );
					results.setMarathon(marathon);
				}
				Run run = new Run();
				run.setFinishingSeconds( resultSet.getInt("finishing_seconds") );
				run.setRunner( RunnerDAO.extractRunner(resultSet,"runner_",false) );
				runs.add(run);
			}
			results.setRuns(runs);
			return results;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}


	public void updateResults(String marathon, Map<String,String> mappedResultsByRunnerId) throws SQLException {
		//System.out.println("Update in DB:"+ marathon+": "+mappedResultsByRunnerId);
		PreparedStatement statement = null;
		try {
			long marathonId = Long.parseLong(marathon);
			statement = this.connection.prepareStatement("UPDATE run SET finishing_seconds = ? WHERE marathon_id = ? AND runner_id = ?");
			for (Map.Entry<String,String> entry : mappedResultsByRunnerId.entrySet()) {
				if (entry.getValue() != null && entry.getValue().trim().length() > 0) {
					long runnerId = Long.parseLong(entry.getKey());
					String[] splitted = entry.getValue().split(":");
					int hours = Integer.parseInt(splitted[0].trim());
					int minutes = Integer.parseInt(splitted[1].trim());
					int seconds = Integer.parseInt(splitted[2].trim());
					int totalSeconds = hours*3600 + minutes*60 + seconds;
					
					statement.clearParameters();
					statement.setInt(1, totalSeconds);
					statement.setLong(2, marathonId);
					statement.setLong(3, runnerId);
					statement.executeUpdate();
				}
			}
		} finally {
			if (statement != null) statement.close();
		}
	}

	public void deleteResults(String marathon) throws SQLException {
		PreparedStatement statement = null;
		try {
			long marathonId = Long.parseLong(marathon);
			statement = this.connection.prepareStatement("UPDATE run SET finishing_seconds = NULL WHERE marathon_id = ?");
			statement.setLong(1, marathonId);
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}


	public void updateResult(String marathon, long runnerId, int secondsFinished) throws SQLException {
		PreparedStatement statement = null;
		try {
			long marathonId = Long.parseLong(marathon);
			statement = this.connection.prepareStatement("UPDATE run SET finishing_seconds = ? WHERE marathon_id = ? AND runner_id = ?");
			statement.setInt(1, secondsFinished);
			statement.setLong(2, marathonId);
			statement.setLong(3, runnerId);
			statement.executeUpdate();
		} finally {
			if (statement != null) statement.close();
		}
	}
	
}
