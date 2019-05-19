package demo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RunnerPhotoImportForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String importPhotoFromURL;

	public String getImportPhotoFromURL() {
		return importPhotoFromURL;
	}

	public void setImportPhotoFromURL(String importPhotoFromURL) {
		this.importPhotoFromURL = importPhotoFromURL;
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
