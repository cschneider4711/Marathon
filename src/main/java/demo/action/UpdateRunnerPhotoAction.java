package demo.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import demo.dao.DAOUtils;
import demo.dao.RunnerDAO;
import demo.form.RunnerPhotoForm;
import demo.pojo.Runner;
import demo.pojo.Upload;

public class UpdateRunnerPhotoAction extends Action {

	public static final String FORWARD_showRunnerProfile = "showRunnerProfile";

	private static final boolean WINDOWS = System.getProperty("os.name").toLowerCase().contains("windows");

	public static File getPhotoFolder(ServletContext context) throws IOException {
		File result;
		String configValue = context.getInitParameter("PhotoFolder");
		if (configValue == null) {
			result = new File(System.getProperty("user.home"), "marathonImages");
		} else {
			result = new File(configValue);
		} 
		if (!result.exists()) {
			result.mkdirs();
			byte[] defaultImage = IOUtils.toByteArray(UpdateRunnerPhotoAction.class.getResourceAsStream("/demo/util/default.png"));
			FileUtils.writeByteArrayToFile(new File(result,"default.png"), defaultImage);
		}
		return result;
	}

	public static File getVirusScannerScript(ServletContext context) throws IOException {
		File result;
		String configValue = context.getInitParameter("VirusScannerScript");
		final String scriptName = WINDOWS ? "scanFile.cmd" : "scanFile.sh";
		if (configValue == null) {
			result = new File(new File(System.getProperty("user.home"), "marathonScripts"), scriptName);
		} else {
			result = new File(configValue);
		} 
		if (!result.exists()) {
			System.err.println("Not existing: "+result.getAbsolutePath());
			result.getParentFile().mkdirs();
			byte[] scriptSource = IOUtils.toByteArray(UpdateRunnerPhotoAction.class.getResourceAsStream("/demo/antivirus/"+scriptName));
			FileUtils.writeByteArrayToFile(result, scriptSource);
		}
		if (!result.exists()) {
			throw new IllegalArgumentException("Virus scanner script not found");
		}
		return result;
	}
	
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, InterruptedException {
		final String runnerUsername = request.getUserPrincipal().getName();
		Runner runner = null;

		RunnerPhotoForm photoForm = (RunnerPhotoForm) form;
		
		Connection connection = null;
		try {
			connection = DAOUtils.getConnection();
			RunnerDAO runnerDAO = new RunnerDAO(connection);
			runner = runnerDAO.loadRunnerByName(runnerUsername);
			
			FormFile uploadedPhotoFile = photoForm.getPhotoFile();
			final File photoFolder = getPhotoFolder(getServlet().getServletContext());
			final File virusScannerScript = getVirusScannerScript(getServlet().getServletContext());
			if (uploadedPhotoFile == null) {
				throw new IllegalArgumentException("No file uploaded");
			}
			Upload upload = new Upload(uploadedPhotoFile.getContentType(), uploadedPhotoFile.getFileName(), uploadedPhotoFile.getFileData());
			checkAndSaveImage(photoFolder, upload, runnerUsername, photoForm.getTypeOfImage(), virusScannerScript);
			
			runner.setPhotoName(upload.getName());
			runnerDAO.updatePhoto(runner);
		} finally {
			if (connection != null) connection.close();
		}
		
		request.setAttribute("runner", runner);
		return mapping.findForward(FORWARD_showRunnerProfile);
	}

	static void checkAndSaveImage(File photoFolder, Upload uploadedPhotoFile, String username, String typeOfImage, File virusScannerScript) throws IOException, InterruptedException {
//		System.out.println(uploadedPhotoFile.getContentType());
//		System.out.println(uploadedPhotoFile.getFileName());
//		System.out.println(uploadedPhotoFile.getFileSize());
		if (!uploadedPhotoFile.getContentType().toLowerCase().startsWith("image/")) {
			throw new IllegalArgumentException("Upload is not of expected type image");
		}
		
		// convert SVG avatar file to PNG image
		if ("SVG".equals(typeOfImage)) {
			try {
				uploadedPhotoFile.setData( rasterizeSVG( uploadedPhotoFile.getData() ) );
				uploadedPhotoFile.setName( uploadedPhotoFile.getName()+".png" );
			} catch (TranscoderException e) {
				throw new IOException("Unable to encode SVG", e);
			}
		}
		
		// save uploaded-file for scanner
		if (!photoFolder.exists()) {
			photoFolder.mkdir();
		}
		File targetFile = new File(photoFolder, uploadedPhotoFile.getName());
		FileUtils.writeByteArrayToFile(targetFile, uploadedPhotoFile.getData());
		
		// virus-scan uploaded file
		boolean imageOK = false;
		try {
			imageOK = scanFile(targetFile, username, typeOfImage, virusScannerScript);
			if (imageOK) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!imageOK) {
				targetFile.delete();
				throw new IllegalArgumentException("Upload was not checked with success");
			}
		}
	}



	private static byte[] rasterizeSVG(byte[] data) throws TranscoderException {
        PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(data));
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(ostream);
        t.transcode(input, output);
        return ostream.toByteArray();
    }



	private static class StreamGobbler extends Thread {
		InputStream is;
		StreamGobbler(InputStream is) {
			this.is = is;
		}
	
		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					// do nothing, just catch stream
					System.out.println(line);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}	
	
	static boolean scanFile(File targetFile, String username, String typeOfImage, File virusScannerScript) throws IOException, InterruptedException {
		//final File virusScannerWrapperScriptFolder = new File(System.getProperty("user.home"),"marathonScripts");
		//File virusScannerScript = new File(virusScannerWrapperScriptFolder, "scanFile.sh");
		String filename = targetFile.getAbsolutePath();
		Process proc;
		
		if (WINDOWS) {
			proc = Runtime.getRuntime().exec("cmd.exe /c \"\""+
					virusScannerScript.getAbsolutePath()+"\" "+typeOfImage+" \""+filename+"\" "+username+"\"");
		} else {
			String[] command = new String[] { "/bin/sh", "-c",
					"\""+virusScannerScript.getAbsolutePath()+"\" "+typeOfImage+" \""+filename+"\" "+username };
			proc = Runtime.getRuntime().exec(command, null, null);
		}


		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());

		errorGobbler.start();
		outputGobbler.start();

		int exitCode = proc.waitFor();
		System.out.println("Exit code from scanner was: "+exitCode);
		return exitCode == 0;
	}

	
	
}
