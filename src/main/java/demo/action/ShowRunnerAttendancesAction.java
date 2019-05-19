package demo.action;

import java.sql.Connection;
import java.sql.SQLException;
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

public class ShowRunnerAttendancesAction extends Action {

	public static final String FORWARD_showRunnerAttendances = "showRunnerAttendances";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		final String runnerUsername = request.getUserPrincipal().getName();
		
		final Runner runner;
		final List<Attendance> attendances;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			AttendanceDAO attendanceDAO = new AttendanceDAO(connection);

			runner = runnerDAO.loadRunnerByName(runnerUsername);
			attendances = attendanceDAO.loadAttendingMarathons(runner);
			
			RunnerAttendancesForm runnerAttendancesForm = (RunnerAttendancesForm)form;
			runnerAttendancesForm.setAttendances(attendances);
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runner", runner);
		request.setAttribute("attendancesList", attendances);
		return mapping.findForward(FORWARD_showRunnerAttendances);
	}

	
	
}
