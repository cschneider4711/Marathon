package demo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class ChangePasswordForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;
	
	private String password, passwordAgain;
	
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.password = null;
		this.passwordAgain = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		if (this.password == null || this.passwordAgain == null || this.password.trim().length() == 0 || !this.password.equals(this.passwordAgain)) {
			ActionErrors errors = new ActionErrors();
			errors.add("password", new ActionMessage("password.change.mismatch"));
			return errors;
		}
		return super.validate(mapping, request);
	}
	
	
	
}
