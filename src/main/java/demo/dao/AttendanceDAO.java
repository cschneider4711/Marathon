package demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.pojo.Attendance;
import demo.pojo.Marathon;
import demo.pojo.Runner;

public class AttendanceDAO {

	private Connection connection;

	public AttendanceDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public List<Attendance> loadAttendingMarathons(Runner runner) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT marathon.id, marathon.title, run.runner_id " +
					" FROM marathon marathon LEFT OUTER JOIN run run " +
					" ON marathon.id = run.marathon_id AND run.runner_id = ?");
			statement.setLong(1, runner.getId());
			resultSet = statement.executeQuery();
			
			final List<Attendance> attendances = new ArrayList<Attendance>();
			while (resultSet.next()) {
				Marathon marathon = new Marathon();
				marathon.setId( resultSet.getLong("id") );
				marathon.setTitle( resultSet.getString("title") );
				
				Attendance attendance = new Attendance();
				attendance.setMarathon(marathon);
				attendance.setAttending( resultSet.getString("runner_id") != null );

				attendances.add(attendance);
			}
			return attendances;
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
	}

	public void updateAttendances(Runner runner, List<Attendance> attendances) throws SQLException {
		List<Attendance> existingAttendances = loadAttendingMarathons(runner);
		
		PreparedStatement statementDelete = null, statementInsert = null;
		try {
			statementDelete = this.connection.prepareStatement("DELETE FROM run WHERE runner_id = ? AND marathon_id = ?");
			statementInsert = this.connection.prepareStatement("INSERT INTO run (runner_id, marathon_id) VALUES (?, ?)");

			for (Attendance attendance : attendances) {
				Attendance existingAttendance = extractAttendanceByMarathon(existingAttendances, attendance.getMarathon());
				statementDelete.clearParameters();
				statementInsert.clearParameters();
				
				if (attendance.isAttending()) {
					if (!existingAttendance.isAttending()) {
						statementInsert.setLong(1, runner.getId());
						statementInsert.setLong(2, attendance.getMarathon().getId());
						statementInsert.executeUpdate();
					}
				} else {
					if (existingAttendance.isAttending()) {
						statementDelete.setLong(1, runner.getId());
						statementDelete.setLong(2, attendance.getMarathon().getId());
						statementDelete.executeUpdate();
					}
				}
			}
		} finally {
			if (statementDelete != null) statementDelete.close();
			if (statementInsert != null) statementInsert.close();
		}
	}
	
	
	

	private static Attendance extractAttendanceByMarathon(
			List<Attendance> attendances, Marathon marathon) {
		for (Attendance attendance : attendances) {
			if (attendance.getMarathon().getId().equals(marathon.getId())) 
				return attendance;
		}
		return null;
	}

		
}
