package demo.action;

import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;

import java.util.Map;
import java.util.Base64;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.thoughtworks.xstream.XStream;

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
			runnerDAO.updateRunner(runner, false);
			runner = runnerDAO.loadRunner(runner.getId());
			
			String marshalledBase64 = request.getParameter("state");
			if (marshalledBase64 != null && marshalledBase64.trim().length() > 0) {
				try {
					unmarshalInput(marshalledBase64.trim());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runner", runner);
		request.setAttribute("UpdateResultMessage", "Your data has been saved...");
		return mapping.findForward(FORWARD_showRunnerProfile);
	}

	private void unmarshalInput(String input) throws IOException, ClassNotFoundException {
		// Base64 decode it
		String xml = new String(Base64.getDecoder().decode(input));
		// Unmarshal and cast to expected object (completely unrelated to the Gadget)
		// could be any object, just cast it
		XStream xstream = new XStream();
		Map someMap = (Map) xstream.fromXML(xml);
		// and use it as expected by the business usecase...
		// ... ... ...
		// ... ... ...
	}

	
	
}
