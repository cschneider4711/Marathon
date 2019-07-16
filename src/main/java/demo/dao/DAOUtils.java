package demo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DAOUtils {

	public static Connection getConnection() throws NamingException, SQLException {
		// Obtain our environment naming context
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		DataSource datasource = (DataSource) envCtx.lookup("jdbc/MarathonDS");

		// Allocate and use a connection from the pool
		return datasource.getConnection();
	}
	
	public static boolean isAlive() {
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			return connection.isValid(10) && new RunnerDAO(connection).loadRunner(1) != null;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignored) {}
		}
	}
	
	
	/**
	 * formats as literal for example 1976-5-19 from 19.5.1976
	 * @param dateInputDDMMYYYY
	 * @return
	 */
	public static String getDateForHSQLDB(String dateInputDDMMYYYY) {
		if (dateInputDDMMYYYY == null) return null;
		String[] tokens = dateInputDDMMYYYY.split("\\.");
		StringBuilder result = new StringBuilder();
		result.append(tokens[2]).append('-')
		.append(tokens[1]).append('-')
		.append(tokens[0]);
		return result.toString();
	}
	
	private DAOUtils() {}

}
