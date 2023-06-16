package demo.action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.RunnerForm;
import demo.pojo.Runner;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditRunnerPhotoAction extends Action {

	public static final String FORWARD_editRunnerPhoto = "editRunnerPhoto";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		String runnerUsername = request.getUserPrincipal().getName();

		final Runner runner;

		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			runner = runnerDAO.loadRunnerByName(runnerUsername);
		} finally {
			if (connection != null) connection.close();
		}

		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_editRunnerPhoto);
	}

	
	
}
