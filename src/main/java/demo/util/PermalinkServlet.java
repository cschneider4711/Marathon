package demo.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PermalinkServlet
 */
public class PermalinkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PermalinkServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String permalinkTarget = request.getParameter("act");
		// used to have a central servlet for incoming external permalinked pages
		// in order to be flexible for our internals to change 
		// (i.e. possibility to adjust in permalink servlet before redirecting)
//		response.sendRedirect("http://"+request.getServerName()+permalinkTarget);
		response.sendRedirect(permalinkTarget);
	}

}
