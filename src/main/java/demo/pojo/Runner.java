package demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class Runner implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username, firstname, lastname, street, zip, city, creditCardNumber, photoName;
	private Date dateOfBirth;
	
	public Runner() {
		super();
	}
	
	public Runner(Long id, String username, String firstname, String lastname, String street, String zip,
			String city, Date dateOfBirth, String creditCardNumber) {
		this();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.dateOfBirth = dateOfBirth;
		this.creditCardNumber = creditCardNumber;
	}
	
	public Runner(Long id, String username, String firstname, String lastname, String street, String zip,
			String city, Date dateOfBirth, String creditCardNumber, String photoName) {
		this(id, username, firstname, lastname, street, zip, city, dateOfBirth, creditCardNumber);
		this.photoName = photoName;
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
	public String getStreet() {
		return this.street;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	@Override
	public String toString() {
		return "Runner [id=" + id + ", username=" + username + ", firstname="
				+ firstname + ", lastname=" + lastname + ", street=" + street
				+ ", zip=" + zip + ", city=" + city + ", creditCardNumber="
				+ creditCardNumber + ", photoName=" + photoName
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime
				* result
				+ ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime * result
				+ ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((photoName == null) ? 0 : photoName.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Runner other = (Runner) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (photoName == null) {
			if (other.photoName != null)
				return false;
		} else if (!photoName.equals(other.photoName))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}


	
	
}
