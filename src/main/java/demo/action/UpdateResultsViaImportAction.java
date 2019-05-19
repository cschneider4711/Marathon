package demo.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.ZipInputStream;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import demo.dao.DAOUtils;
import demo.dao.ResultsDAO;
import demo.form.ResultsImportForm;

public class UpdateResultsViaImportAction extends Action {

	public static final String FORWARD_showResults = "showResults";


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, InterruptedException, ParserConfigurationException, SAXException {
		ResultsImportForm resultsImportForm = (ResultsImportForm) form;
		String contentType = resultsImportForm.getXmlResultsFile().getContentType();
		StringBuilder resultMessage = new StringBuilder("Invalid upload XML");
		final boolean isZip = contentType.toLowerCase().contains("zip");//"application/x-zip-compressed".equalsIgnoreCase(contentType);
		if ("text/xml".equalsIgnoreCase(contentType) || "application/xml".equalsIgnoreCase(contentType) || isZip) {
			InputStream input;
			if (isZip) {
				input = extractFromZip(resultsImportForm.getXmlResultsFile().getInputStream());
			} else {
				input = resultsImportForm.getXmlResultsFile().getInputStream(); 
			}
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(input);
			Document document = documentBuilder.parse(inputSource);
			document.getDocumentElement().normalize();
			String titleOfImport = extract(document, "import");

			int importCounter = 0;
			NodeList results = document.getElementsByTagName("runner-result");
			if (results != null) {
				Connection connection = null;
				boolean rollback = true;
				try {
					connection = DAOUtils.getConnection();
					connection.setAutoCommit(false);

					ResultsDAO resultsDAO = new ResultsDAO(connection);
					for (int i=0; i<results.getLength(); i++) {
						Node result = results.item(i);
						long runnerId = extractAttributeLongValue(result, "runner-id");
						long secondsFinished = extractAttributeLongValue(result, "seconds");
						resultsDAO.updateResult(resultsImportForm.getMarathon(), runnerId, (int)secondsFinished);
						importCounter++;
					}

					connection.commit();
					rollback = false;
				} finally {
					if (connection != null) {
						if (rollback) connection.rollback();
						connection.close();
					}
				}
			}
			
			resultMessage = new StringBuilder(" Import ");
			resultMessage.append(titleOfImport);
			resultMessage.append(" successful with ");
			resultMessage.append(importCounter);
			resultMessage.append(" rows ");
		}
		request.setAttribute("ImportResultMessage", resultMessage.toString());
		request.setAttribute("marathon", resultsImportForm.getMarathon());
		return mapping.findForward(FORWARD_showResults);
	}

	private String extract(Document document, String tagName) {
		return document.getElementsByTagName(tagName).item(0).getChildNodes()
				.item(0).getNodeValue();
	}

	/*
	private String extract(Element element, String tagName) {
		return element.getElementsByTagName(tagName).item(0).getChildNodes()
				.item(0).getNodeValue();
	}*/

	private long extractAttributeLongValue(Node node, String attributeName) {
		return Long.parseLong(node.getAttributes().getNamedItem(attributeName).getNodeValue());
	}

	private InputStream extractFromZip(InputStream inputStream) throws IOException {
		ZipInputStream zipStream = new ZipInputStream(inputStream);
		/*ZipEntry zipEntry = */zipStream.getNextEntry(); // positions the stream properly
		return zipStream;
	}
	
}
