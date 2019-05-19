package demo.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.RunnerPhotoImportForm;
import demo.pojo.Runner;
import demo.pojo.Upload;

public class UpdateRunnerPhotoViaImportAction extends Action {

	public static final String FORWARD_showRunnerProfile = "showRunnerProfile";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, NamingException,
			InterruptedException {
		final String runnerUsername = request.getUserPrincipal().getName();
		Runner runner = null;

		RunnerPhotoImportForm photoImportForm = (RunnerPhotoImportForm) form;

		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			runner = runnerDAO.loadRunnerByName(runnerUsername);

			// fetch it
			String typeOfImage = "PNG";
			String contentType = "image/png";
			Upload photo = fetchPhoto(photoImportForm.getImportPhotoFromURL(),
					contentType);
			// save it
			final File photoFolder = UpdateRunnerPhotoAction
					.getPhotoFolder(getServlet().getServletContext());
			final File virusScannerScript = UpdateRunnerPhotoAction
					.getVirusScannerScript(getServlet().getServletContext());
			UpdateRunnerPhotoAction.checkAndSaveImage(photoFolder, photo,
					runnerUsername, typeOfImage, virusScannerScript);

			runner.setPhotoName(photo.getName());
			runnerDAO.updatePhoto(runner);
		} finally {
			if (connection != null)
				connection.close();
		}

		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_showRunnerProfile);
	}

	private Upload fetchPhoto(String importPhotoFromURL, String contentType)
			throws IOException {
		final File tmpFile = File.createTempFile("importImageFromURL", "tmp");
		final URL url = new URL(importPhotoFromURL);
		System.out.println("URL protocol: "+url.getProtocol());
		System.out.println("URL host + port: "+url.getHost()+(url.getPort() != -1?(":"+url.getPort()):""));
		saveFileFromURL(url, tmpFile);
		String[] names = url.getPath().split("/");
		final Upload upload = new Upload(contentType, names[names.length-1],
				FileUtils.readFileToByteArray(tmpFile));
		tmpFile.delete();
		return upload;
	}

	public static void saveFileFromURL(URL url, File file)
			throws MalformedURLException, IOException {
		URLConnection connection = url.openConnection();
		
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(connection.getInputStream());
			fout = new FileOutputStream(file);

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}
}
