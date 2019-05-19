package demo.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xml.sax.SAXException;

import demo.dao.DAOUtils;
import demo.dao.ResultsDAO;
import demo.util.SessionListener;

public class DeleteAllResultsAction extends Action {

	public static final String FORWARD_showResults = "showResults";


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, InterruptedException, ParserConfigurationException, SAXException, ServletException {
		// check token for the very sensitive delete-all functionality
		final String expectedSecret = (String) request.getSession().getAttribute(SessionListener.SENSITIVE_ACTIONS_TOKEN);
		if (!expectedSecret.equals(request.getParameter("secret"))) {
			throw new ServletException("Missing or wrong secret");
		}
		
		final String marathon = request.getParameter("marathon");
		request.setAttribute("marathon", marathon);
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			ResultsDAO resultsDAO = new ResultsDAO(connection);
			resultsDAO.deleteResults(marathon);
		} finally {
			if (connection != null) connection.close();
		}
		
		return mapping.findForward(FORWARD_showResults);
	}

}
