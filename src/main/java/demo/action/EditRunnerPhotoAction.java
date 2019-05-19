package demo.action;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditRunnerPhotoAction extends Action {

	public static final String FORWARD_editRunnerPhoto = "editRunnerPhoto";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {

		return mapping.findForward(FORWARD_editRunnerPhoto);
	}

	
	
}
