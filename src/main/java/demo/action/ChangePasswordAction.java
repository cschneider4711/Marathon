package demo.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import demo.dao.DAOUtils;
import demo.dao.SystemDAO;
import demo.form.ChangePasswordForm;
import demo.util.SessionListener;

public class ChangePasswordAction extends Action {

	public static final String FORWARD_changePassword = "changePassword";


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException, NamingException {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		if (request.getMethod().equalsIgnoreCase("POST") 
				&& changePasswordForm.getPassword() != null
				&& changePasswordForm.getPassword().length() > 0
				&& changePasswordForm.getPassword().equals(changePasswordForm.getPasswordAgain())) {
			// check token for the very sensitive delete-all functionality
			final String expectedSecret = (String) request.getSession().getAttribute(SessionListener.SENSITIVE_ACTIONS_TOKEN);
			if (!expectedSecret.equals(request.getParameter("secret"))) {
				throw new ServletException("Missing or wrong secret");
			}

			Connection connection = null;
			try {
				connection = DAOUtils.getConnection();
				SystemDAO systemDAO = new SystemDAO(connection);
				systemDAO.changePassword(request.getUserPrincipal().getName(), changePasswordForm.getPassword());
				request.setAttribute("UpdateResultMessage", "Password has been changed.");
			} finally {
				if (connection != null) connection.close();
			}

			changePasswordForm.setPassword(null);
			changePasswordForm.setPasswordAgain(null);
		}
		return mapping.findForward(FORWARD_changePassword);
	}
	
}
