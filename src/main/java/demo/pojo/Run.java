package demo.pojo;

import java.io.Serializable;

public class Run implements Serializable {
	private static final long serialVersionUID = 1L;

	private Runner runner;
	private Integer finishingSeconds;
	
	
	public Runner getRunner() {
		return runner;
	}
	public void setRunner(Runner runner) {
		this.runner = runner;
	}
	
	
	public Integer getFinishingSeconds() {
		return finishingSeconds;
	}
	public void setFinishingSeconds(Integer finishingSeconds) {
		this.finishingSeconds = finishingSeconds;
	}
	
	
	public Integer getFinishingPixels() {
		return finishingSeconds / 80;
	}

	public String getFinishingTime() {
		if (finishingSeconds == null || finishingSeconds == 0) return null;
		StringBuilder timeString = new StringBuilder();
		int hours = finishingSeconds / 3600;
		int rest =  finishingSeconds - hours*3600;
		int minutes = rest / 60;
		int seconds = rest - minutes*60;
		timeString.append(hours).append(':');
		if (minutes < 10) timeString.append('0');
		timeString.append(minutes).append(':');
		if (seconds < 10) timeString.append('0');
		timeString.append(seconds);
		return timeString.toString();
	}
	
	
	@Override
	public String toString() {
		return "RunBean [runner=" + runner + ", finishingSeconds="
				+ finishingSeconds + "]";
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((finishingSeconds == null) ? 0 : finishingSeconds.hashCode());
		result = prime * result + ((runner == null) ? 0 : runner.hashCode());
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
		Run other = (Run) obj;
		if (finishingSeconds == null) {
			if (other.finishingSeconds != null)
				return false;
		} else if (!finishingSeconds.equals(other.finishingSeconds))
			return false;
		if (runner == null) {
			if (other.runner != null)
				return false;
		} else if (!runner.equals(other.runner))
			return false;
		return true;
	}
	
	
	
}
