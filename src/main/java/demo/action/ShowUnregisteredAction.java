package demo.action;

import demo.dao.DAOUtils;
import demo.dao.MarathonDAO;
import demo.pojo.Marathon;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowUnregisteredAction extends Action {

	public static final String FORWARD_showUnregistered = "showUnregistered";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		return mapping.findForward(FORWARD_showUnregistered);
	}

	
	
}
