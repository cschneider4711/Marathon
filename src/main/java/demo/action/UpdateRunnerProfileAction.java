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
import demo.form.RunnerForm;
import demo.pojo.Runner;

public class UpdateRunnerProfileAction extends Action {

	public static final String FORWARD_showRunnerProfile = "showRunnerProfile";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		Runner runner = null;

		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			RunnerForm runnerForm = (RunnerForm)form;
			runner = new Runner( runnerForm.getId(),
					runnerForm.getUsername(),
					runnerForm.getFirstname(),
					runnerForm.getLastname(),
					runnerForm.getStreet(),
					runnerForm.getZip(),
					runnerForm.getCity(),
					runnerForm.getDateOfBirthAsDate(),
					runnerForm.getCreditcardNumber()
				);
			runnerDAO.updateRunner(runner);
			runner = runnerDAO.loadRunner(runner.getId());
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runner", runner);
		request.setAttribute("UpdateResultMessage", "Your data has been saved...");
		return mapping.findForward(FORWARD_showRunnerProfile);
	}

	
	
}
