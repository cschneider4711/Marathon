package demo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ResultsImportForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private FormFile xmlResultsFile;
	private String marathon;
	
	
	
	public FormFile getXmlResultsFile() {
		return xmlResultsFile;
	}

	public void setXmlResultsFile(FormFile xmlResultsFile) {
		this.xmlResultsFile = xmlResultsFile;
	}

	public String getMarathon() {
		return marathon;
	}

	public void setMarathon(String marathon) {
		this.marathon = marathon;
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
