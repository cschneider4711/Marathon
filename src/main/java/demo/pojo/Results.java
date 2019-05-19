package demo.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Results implements Serializable {
	private static final long serialVersionUID = 1L;

	private Marathon marathon;
	private List<Run> runs = new ArrayList<Run>();
	
	
	public Results() {
		super();
	}
	
	public Results(Marathon marathon, List<Run> runs) {
		this();
		this.marathon = marathon;
		this.runs = runs;
	}
	
	
	public Marathon getMarathon() {
		return marathon;
	}
	public void setMarathon(Marathon marathon) {
		this.marathon = marathon;
	}
	public List<Run> getRuns() {
		return runs;
	}
	public void setRuns(List<Run> runs) {
		this.runs = runs;
	}
	
	
	
	@Override
	public String toString() {
		return "ResultsBean [marathon=" + marathon + ", runs=" + runs + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((marathon == null) ? 0 : marathon.hashCode());
		result = prime * result + ((runs == null) ? 0 : runs.hashCode());
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
		Results other = (Results) obj;
		if (marathon == null) {
			if (other.marathon != null)
				return false;
		} else if (!marathon.equals(other.marathon))
			return false;
		if (runs == null) {
			if (other.runs != null)
				return false;
		} else if (!runs.equals(other.runs))
			return false;
		return true;
	}
	
}
