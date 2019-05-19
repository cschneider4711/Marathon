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
import demo.dao.SystemDAO;
import demo.form.CreateAccountForm;

public class CreateAccountAction extends Action {

	public static final String FORWARD_confirm = "confirm";
	public static final String FORWARD_profile = "profile";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
		
		final CreateAccountForm createAccountForm = (CreateAccountForm) form;
		if (createAccountForm.isConfirmed()) {
			
			String username = createAccountForm.getUsername();
			String firstname = createAccountForm.getFirstname();
			String lastname = createAccountForm.getLastname();
			String street = createAccountForm.getStreet();
			String zip = createAccountForm.getZip();
			String city = createAccountForm.getCity();
			String creditcardNumber = createAccountForm.getCreditcardNumber();
			String dateOfBirth = createAccountForm.getDateOfBirth();

			String password = createAccountForm.getPassword();

			Connection connection = null;
			boolean rollback = true;
			try {
				connection = DAOUtils.getConnection();
				connection.setAutoCommit(false);
				
				// create the runner row
				RunnerDAO runnerDAO = new RunnerDAO(connection);
				runnerDAO.createRunner(username, firstname, lastname, 
						street, zip, city, creditcardNumber, dateOfBirth);

				// create the account
				SystemDAO systemDAO = new SystemDAO(connection);
				systemDAO.createAccount(username, password);
				
				connection.commit();
				rollback = false;
			} finally {
				if (connection != null) {
					if (rollback) connection.rollback();
					connection.close();
				}
			}
			
			createAccountForm.clear();
			
			return mapping.findForward(FORWARD_profile);
		} else {
			return mapping.findForward(FORWARD_confirm);
		}
	}

	
	
}
