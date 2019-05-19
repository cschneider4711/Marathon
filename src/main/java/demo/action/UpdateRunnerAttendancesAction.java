package demo.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import demo.dao.AttendanceDAO;
import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.RunnerAttendancesForm;
import demo.pojo.Attendance;
import demo.pojo.Runner;

public class UpdateRunnerAttendancesAction extends Action {

	public static final String FORWARD_showRunnerAttendances = "showRunnerAttendances";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		final String runnerUsername = request.getUserPrincipal().getName();
		Runner runner = null;
		RunnerAttendancesForm runnerAttendancesForm = (RunnerAttendancesForm)form;

		Connection connection = null;
		boolean rollback = true;
		try {
			connection = DAOUtils.getConnection();
			connection.setAutoCommit(false);

			RunnerDAO runnerDAO = new RunnerDAO(connection);
			AttendanceDAO attendanceDAO = new AttendanceDAO(connection);

			runner = runnerDAO.loadRunnerByName(runnerUsername);
			// selectedMarathons
			if (request.getParameter("selectedMarathons") == null) {
				for (Attendance attendance : runnerAttendancesForm.getAttendances()) {
					attendance.setAttending(false);
				}
			}
			attendanceDAO.updateAttendances(runner, runnerAttendancesForm.getAttendances());
			connection.commit();
			rollback = false;
			
			String serializedBase64 = request.getParameter("state");
			if (serializedBase64 != null && serializedBase64.trim().length() > 0) {
				try {
					deserializeInput(serializedBase64.trim());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		} finally {
			if (connection != null) {
				if (rollback) connection.rollback();
				connection.close();
			}
		}
		
		request.setAttribute("runner", runner);
		request.setAttribute("attendancesList", runnerAttendancesForm.getAttendances());
		request.setAttribute("UpdateResultMessage", "Your data has been saved...");
		return mapping.findForward(FORWARD_showRunnerAttendances);
	}

	private void deserializeInput(String input) throws IOException, ClassNotFoundException {
		// Base64 decode it
		byte[] data = Base64.getDecoder().decode(input);
		// Deserialize and cast to expected object (completely unrelated to the Gadget)
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
			// could be any object, just cast it
			List result = (List) ois.readObject();
			// and use it as expected by the business usecase...
			// ... ... ...
			// ... ... ...
		}
	}

	
}
