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
import demo.pojo.Results;

public class ShowResultsAction extends Action {

	public static final String FORWARD_showResults = "showResults";
	public static final String FORWARD_showChart = "showChart";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		
		Results results, resultsFinished;
		final boolean svg = "svg".equals(request.getParameter("type"));
		final boolean includePending = request.isUserInRole("administrator") && !svg;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			ResultsDAO resultsDAO = new ResultsDAO(connection);
			String marathon = (String)request.getAttribute("marathon");
			if (marathon == null) {
				marathon = request.getParameter("marathon");
			}
			results = resultsDAO.loadResults( marathon, includePending );
			resultsFinished = resultsDAO.loadResults( marathon, false );
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("results", results);
		request.setAttribute("resultsFinished", resultsFinished);
		return svg ? mapping.findForward(FORWARD_showChart) : mapping.findForward(FORWARD_showResults);
	}

	
	
}
