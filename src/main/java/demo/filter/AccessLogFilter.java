package demo.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class AccessLogFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(AccessLogFilter.class.getName());

	private File accessLogFolder, accessLogFile;
	private BufferedWriter logWriter;
	
    /**
     * Default constructor. 
     */
    public AccessLogFilter() {
    	accessLogFolder = new File("accessLogs");
    }
    

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		try {
			String folderName = config.getInitParameter("AccessLogFolder");
			if (folderName != null) {
				accessLogFolder = new File(folderName);
			}
			LOG.info("Using folder for access logs: "+accessLogFolder.getAbsolutePath());
			if (!accessLogFolder.exists()) {
				accessLogFolder.mkdir();
			}
			accessLogFile = new File(accessLogFolder, "marathon-"+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))+".log");
			logWriter = new BufferedWriter(new FileWriter(accessLogFile));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Unable to open custom access log due to exception", e);
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		if (logWriter != null) {
			try {
				logWriter.close();
			} catch (IOException e) {
				LOG.log(Level.WARNING, "Unable to close custom access log due to exception", e);
			}
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String applicationArea = httpRequest.getRequestURI();
		int posLastSlash = applicationArea.lastIndexOf('/');
		if (posLastSlash != -1) {
			applicationArea = applicationArea.substring(0, posLastSlash);
		}
		String userAgent = httpRequest.getHeader("User-Agent");
		StringBuilder line = new StringBuilder(applicationArea);
		line.append(',').append(userAgent).append('\n');
		logWriter.write(line.toString());
		logWriter.flush();
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}
