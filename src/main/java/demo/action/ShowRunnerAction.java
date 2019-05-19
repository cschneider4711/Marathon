package demo.action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.pojo.Runner;

public class ShowRunnerAction extends Action {

	public static final String FORWARD_showRunner = "showRunner";
	public static final String FORWARD_showError = "showError";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		final String runnerId = request.getParameter("runner");
		
		final Runner runner;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			if (runnerDAO.hasRunnerFinished(runnerId)) {
				// TODO: Style the runner's profile with a star "has finished"
				System.out.println("Accessing a runner's profile who has finished");
			}
			runner = runnerDAO.loadRunner(Long.parseLong(runnerId));
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward(FORWARD_showError);
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_showRunner);
	}

	
	
}
