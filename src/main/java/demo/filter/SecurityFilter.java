package demo.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.regex.*;


/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(SecurityFilter.class.getName());

	private static final String CHECK_TIME = "CHECK_TIME";
	private static final String CHECK_STRING = "CHECK_STRING";
	private static final String CHECK_STRING_PREVIOUS = "CHECK_STRING_PREVIOUS";
	
	private Pattern validationPattern = null;
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (!isValidRequest(httpRequest)) {
			return;
		}
		
		String paramValues = logRequestParams(httpRequest);
		checkTime(httpRequest, paramValues);
		addAntiCacheHeader(httpRequest, httpResponse);
		
		String uri = httpRequest.getRequestURI();
		if ("/marathon/check".equals(uri)) {
			Long checkTime = (Long)httpRequest.getServletContext().getAttribute(CHECK_TIME);
			if (checkTime != null) {
				long idle = (System.currentTimeMillis() - checkTime) / 1000L;
				httpResponse.getWriter().print(idle);
				httpResponse.getWriter().print(" ");
				httpResponse.getWriter().print(httpRequest.getServletContext().getAttribute(CHECK_STRING)); // also XSS here
				httpResponse.getWriter().print("  //  ");
				httpResponse.getWriter().print(httpRequest.getServletContext().getAttribute(CHECK_STRING_PREVIOUS)); // also XSS here
			}
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	
	
	private void checkTime(HttpServletRequest httpRequest, String paramValues) {
		try {
			String uri = httpRequest.getRequestURI();
			if (!"/marathon/monitoring".equals(uri) && !"/marathon/check".equals(uri)) {
				Long checkTime = System.currentTimeMillis();
				ServletContext servletContext = httpRequest.getServletContext();
				servletContext.setAttribute(CHECK_TIME, checkTime);
				StringBuilder checkString = new StringBuilder(uri.replace("/marathon/", "")).append(":");
				if (paramValues != null && paramValues.length() > 0) {
					checkString.append(paramValues);
				}
				servletContext.setAttribute(CHECK_STRING_PREVIOUS, servletContext.getAttribute(CHECK_STRING));
				servletContext.setAttribute(CHECK_STRING, checkString.toString().replaceAll("\\s+"," "));
			}
		} catch (Exception e) {
			System.err.println("Unable to perform check time method: "+e);
		}
	}


	private boolean isValidRequest(HttpServletRequest httpRequest) {
		try {
			if (httpRequest != null && validationPattern != null) {
				Enumeration<String> paramNames = httpRequest.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String name = paramNames.nextElement();
					String value = httpRequest.getParameter(name);
					Matcher matcher = validationPattern.matcher(value);
					if (matcher.find()) {
						System.out.println("REQUEST VALIDATION FAIL: "+value);
						return false;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Unable to perform validation check: "+e);
		}
		return true;
	}


	// also bad: too excessive logging (logs also sensitive data etc.)
	private String logRequestParams(HttpServletRequest httpRequest) {
		try {
			StringBuilder paramValues = new StringBuilder();
			String logPrefix = System.getenv("MARATHON_LOG_PREFIX");
			if (httpRequest != null && logPrefix != null && logPrefix.trim().length() > 0 && !logPrefix.equals("OFF")) {
				Enumeration<String> paramNames = httpRequest.getParameterNames();
				StringBuilder appender = new StringBuilder();
				while (paramNames.hasMoreElements()) {
					String name = paramNames.nextElement();
					String value = httpRequest.getParameter(name);
					paramValues.append("  ").append(value);
					appender.append(logPrefix).append(" | ").append(name).append(": ").append(value).append("\n");
				}
				if (appender.length() > 0) {
					appender.append("------------");
					System.out.println(appender.toString());
				}
			}
			return paramValues.toString();
		} catch (Exception e) {
			System.err.println("Unable to perform request logging: "+e);
			return null;
		}
	}



	private void addAntiCacheHeader(HttpServletRequest request, HttpServletResponse response) {
		final String requestedURI = request.getRequestURI();
		if (requestedURI != null && !requestedURI.endsWith(".css") 
				&& !requestedURI.endsWith(".png") 
				&& !requestedURI.endsWith(".jpg") 
				&& !requestedURI.endsWith(".jpeg") 
				&& !requestedURI.endsWith(".js") 
				&& !requestedURI.endsWith("JavaScriptServlet")) {
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
		}
	}



	public void init(FilterConfig config) throws ServletException {
		Locale.setDefault(Locale.ENGLISH);
		config.getServletContext().setAttribute(CHECK_STRING, "");
		config.getServletContext().setAttribute(CHECK_STRING_PREVIOUS, "");

		String logPrefix = System.getenv("MARATHON_LOG_PREFIX");
		if (logPrefix != null && logPrefix.trim().length() > 0 && !logPrefix.equals("OFF")) {
			System.out.println(logPrefix+" INITIALIZED");
		}

		String regEx = System.getenv("VALIDATION_REGEX");
		if (regEx != null && regEx.trim().length() > 0) {
			System.out.println("REGEX VALIDATION: "+regEx);
			validationPattern = Pattern.compile(regEx);
		}
	}

	public void destroy() {
		String logPrefix = System.getenv("MARATHON_LOG_PREFIX");
		if (logPrefix != null && logPrefix.trim().length() > 0 && !logPrefix.equals("OFF")) {
			System.out.println(logPrefix+" DESTROYED");
		}
	}

}
