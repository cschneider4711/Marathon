package demo.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import demo.pojo.Runner;

public class RunnerForm extends /*ActionForm*/ValidatorForm {
	private static final long serialVersionUID = 1L;
	
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	
	
	private Long id;
	private String username, firstname, lastname, street, zip, city, creditcardNumber;
	private Date dateOfBirth;
	
	
	public void setRunner(Runner runner) {
		if (runner != null) {
			this.id = runner.getId();
			this.username = runner.getUsername();
			this.firstname = runner.getFirstname();
			this.lastname = runner.getLastname();
			this.street = runner.getStreet();
			this.zip = runner.getZip();
			this.city = runner.getCity();
			this.creditcardNumber = runner.getCreditCardNumber();
			this.dateOfBirth = runner.getDateOfBirth();
		}
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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




	public String getCreditcardNumber() {
		return creditcardNumber;
	}
	public void setCreditcardNumber(String creditcardNumber) {
		this.creditcardNumber = creditcardNumber;
	}




	public String getDateOfBirth() {
		if (dateOfBirth == null) {
			return null;
		}
		return new SimpleDateFormat(DATE_FORMAT).format(dateOfBirth);
	}
	public Date getDateOfBirthAsDate() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) throws ParseException {
		this.dateOfBirth = new SimpleDateFormat(DATE_FORMAT).parse(dateOfBirth);
	}




	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		id = null;
		username = null;
		firstname = null;
		lastname = null;
		street = null;
		zip = null;
		city = null;
		creditcardNumber = null;
		dateOfBirth = null;
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}
	
	
	
}
