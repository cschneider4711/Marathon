package demo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class RunnerPhotoForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private FormFile photoFile;
	private String typeOfImage;

	public FormFile getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(FormFile photoFile) {
		this.photoFile = photoFile;
	}
	

	public String getTypeOfImage() {
		return typeOfImage;
	}

	public void setTypeOfImage(String typeOfImage) {
		this.typeOfImage = typeOfImage;
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
