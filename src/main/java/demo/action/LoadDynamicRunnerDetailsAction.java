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

public class LoadDynamicRunnerDetailsAction extends Action {

	public static final String FORWARD_dynamicRunnerDetails = "dynamicRunnerDetails";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		final Runner runner;
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			runner = runnerDAO.loadRunner(Long.parseLong(request.getParameter("runner")));
		} finally {
			if (connection != null) connection.close();
		}
		
		
		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_dynamicRunnerDetails);
	}

	
	
}
