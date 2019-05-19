package demo.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ResultsForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String marathon;
	
	private Map<String,String> mappedResults = new HashMap<String, String>();
	
	
	
	public String getMarathon() {
		return marathon;
	}

	public void setMarathon(String marathon) {
		this.marathon = marathon;
	}

	
	
	
	public String getResultsMapped(String key) {
		return mappedResults.get(key);
	}

	public void setResultsMapped(String key, String value) {
		mappedResults.put(key, value);
	}
	
	public Map<String,String> getResultMapComplete() {
		return new HashMap<String, String>(this.mappedResults);
	}

	

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		mappedResults.clear();
		marathon = null;
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}
	
	
	
}
