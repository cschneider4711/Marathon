package demo.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.dao.DAOUtils;

/**
 * Servlet implementation class MonitoringServlet
 */
public class MonitoringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitoringServlet() {
        super();
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		if ("127.0.0.1".equals(request.getRemoteAddr())) {
			writer.println("User Home:");
			writer.println(System.getProperty("user.home"));
			writer.println("Java Home:");
			writer.println(System.getProperty("java.home"));
			writer.println("System:");
			writer.println(super.getServletContext().getServerInfo());
			writer.println("Path:");
			writer.println(super.getServletContext().getRealPath("."));
			writer.println("Database running and with content:");
			writer.println(DAOUtils.isAlive());
		} else {
			writer.println("Sorry, monitoring information only accessible to local clients coming from 127.0.0.1");
		}
	}

}
