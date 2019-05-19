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

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.SearchRunnerForm;
import demo.pojo.Runner;

public class SearchRunnerAction extends Action {

	public static final String FORWARD_showRunners = "showRunners";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		final SearchRunnerForm searchRunnerForm = (SearchRunnerForm) form;
		final String searchTerm = searchRunnerForm.getSearchTerm();
		
		final List<Runner> runners;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			runners = runnerDAO.searchRunners(searchTerm);
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runners", runners);
		request.setAttribute("runnersFound", runners.size());
		return mapping.findForward(FORWARD_showRunners);
	}

	
	
}
