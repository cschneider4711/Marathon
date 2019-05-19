package demo.action;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends Action {

	public static final String FORWARD_index = "index";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		return mapping.findForward(FORWARD_index);
	}

	
	
}
