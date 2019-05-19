package demo.pojo;

import java.io.Serializable;

public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Marathon marathon;
	private boolean attending;
	
	
	public Marathon getMarathon() {
		return marathon;
	}
	public void setMarathon(Marathon marathon) {
		this.marathon = marathon;
	}
	

	public boolean isAttending() {
		return attending;
	}
	public void setAttending(boolean attending) {
		this.attending = attending;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (attending ? 1231 : 1237);
		result = prime * result
				+ ((marathon == null) ? 0 : marathon.hashCode());
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
		Attendance other = (Attendance) obj;
		if (attending != other.attending)
			return false;
		if (marathon == null) {
			if (other.marathon != null)
				return false;
		} else if (!marathon.equals(other.marathon))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Attendance [marathon=" + marathon + ", attending=" + attending
				+ "]";
	}
	
	
	
}
