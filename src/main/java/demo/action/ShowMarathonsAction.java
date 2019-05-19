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
import demo.dao.MarathonDAO;
import demo.pojo.Marathon;

public class ShowMarathonsAction extends Action {

	public static final String FORWARD_showMarathons = "showMarathons";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		
		// TODO think of using caching to avoid hitting the database too often

		final List<Marathon> marathons;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			MarathonDAO marathonDAO = new MarathonDAO(connection);
			marathons = marathonDAO.loadMarathons();
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("marathons", marathons);
		return mapping.findForward(FORWARD_showMarathons);
	}

	
	
}
