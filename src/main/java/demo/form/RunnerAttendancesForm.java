package demo.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import demo.pojo.Attendance;

public class RunnerAttendancesForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	
	private List<Attendance> attendances = new ArrayList<Attendance>();
	
	

	
	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	
	
	
	public String[] getSelectedMarathons() {
		final List<String> selectedMarathons = new ArrayList<String>();
		for (Attendance attendance : attendances) {
			if (attendance.isAttending()) {
				selectedMarathons.add( attendance.getMarathon().getId().toString() );
			}
		}
		return selectedMarathons.toArray(new String[0]);
	}

	public void setSelectedMarathons(String[] selectedMarathons) {
		List<String> selectedMarathonsList = Arrays.asList(selectedMarathons);
		for (Attendance attendance : attendances) {
			attendance.setAttending( selectedMarathonsList.contains( attendance.getMarathon().getId().toString() ) );
		}
	}

	

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}
	
	
	
}
