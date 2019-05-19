package demo.action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.RunnerForm;
import demo.pojo.Runner;

public class ShowRunnerProfileAction extends Action {

	public static final String FORWARD_showRunnerProfile = "showRunnerProfile";
	public static final String FORWARD_showAdminWelcome = "showAdminWelcome";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		String runnerUsername = null;
		Long runnerId = null;
		
		// admin may visit all profiles, normal runner only his/her
		if (request.isUserInRole("administrator")) {
			String runnerIdParam = request.getParameter("runnerId");
			if (runnerIdParam == null) {
				return mapping.findForward(FORWARD_showAdminWelcome);
			}
			runnerId = Long.parseLong(runnerIdParam);
		} else if (request.isUserInRole("runner")) {
			runnerUsername = request.getUserPrincipal().getName();
		} else {
			throw new IllegalArgumentException("Unauthorized to view runner given as param");
		}
		
		
		final Runner runner;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			if (runnerUsername != null) {
				runner = runnerDAO.loadRunnerByName(runnerUsername);
			} else {
				runner = runnerDAO.loadRunner(runnerId);
			}
			RunnerForm runnerForm = (RunnerForm)form;
			runnerForm.setRunner(runner);
		} finally {
			if (connection != null) connection.close();
		}
		
		
		// remember user prefill of form
		if (runnerUsername != null) {
			Cookie userNameRememberer = new Cookie("marathonUserLoginBoxDefault", runnerUsername);
			userNameRememberer.setMaxAge(60*60*24*30);
			response.addCookie(userNameRememberer);
		}
		
		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_showRunnerProfile);
	}

	
	
}
