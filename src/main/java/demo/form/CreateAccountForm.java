package demo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CreateAccountForm extends ActionForm {
	private static final long serialVersionUID = 1L;

    private String username, firstname, lastname, street, zip, city, dateOfBirth, creditcardNumber;	
    private String password;
    private boolean confirmed;
    
    
    
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCreditcardNumber() {
		return creditcardNumber;
	}

	public void setCreditcardNumber(String creditcardNumber) {
		this.creditcardNumber = creditcardNumber;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public void clear() {
		this.username = null;
		this.firstname = null;
		this.lastname = null;
		this.street = null;
		this.zip = null;
		this.city = null;
		this.dateOfBirth = null;
		this.creditcardNumber = null;
		this.password = null;
		this.confirmed = false;
	}	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		/*
		this.username = null;
		this.firstname = null;
		this.lastname = null;
		this.street = null;
		this.zip = null;
		this.city = null;
		this.dateOfBirth = null;
		this.creditcardNumber = null;
		this.password = null;
		this.confirmed = false;
		super.reset(mapping, request);
		*/
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}
	
	
	
}
