package demo.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import demo.action.UpdateRunnerPhotoAction;

/**
 * Servlet implementation class PhotoLoader
 */
public class PhotoLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private File uploadDirectoryFromConfig;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoLoader() {
        super();
    }
    
    

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			uploadDirectoryFromConfig = UpdateRunnerPhotoAction.getPhotoFolder(getServletContext());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String photoName = request.getParameter("photo");
		File photo = new File(uploadDirectoryFromConfig, photoName);
		if (photo.exists()) {
			ServletOutputStream output = response.getOutputStream();
			FileUtils.copyFile(photo, output);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
