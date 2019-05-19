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
import demo.dao.ResultsDAO;
import demo.form.ResultsForm;

public class UpdateResultsAction extends Action {

	public static final String FORWARD_showResults = "showResults";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		ResultsForm resultsForm = (ResultsForm) form;
		
		Connection connection = null;
		boolean rollback = true;
		try {
			connection = DAOUtils.getConnection();
			connection.setAutoCommit(false);

			ResultsDAO resultsDAO = new ResultsDAO(connection);
			resultsDAO.updateResults( resultsForm.getMarathon(), resultsForm.getResultMapComplete() );

			connection.commit();
			rollback = false;
		} finally {
			if (connection != null) {
				if (rollback) connection.rollback();
				connection.close();
			}
		}
		
		return mapping.findForward(FORWARD_showResults);
	}

	
	
}
