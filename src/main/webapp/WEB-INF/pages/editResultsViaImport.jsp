<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Marathon</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>


    <h2>Mass update of results via import</h2>
	
	     
    <b><html:errors /></b>
    
     
	<div class="panel">
	    <div class="panel-header">
	        Upload import XML (plain XML or zipped) of result data:
	    </div>
	    <div class="panel-content">
						    		
		
		    <html:form action="/admin/updateResultsViaImport" method="POST" enctype="multipart/form-data">
			    <html:hidden property="marathon" value="${fn:escapeXml(param.marathon)}"/>
			    
		        <html:file property="xmlResultsFile"/> 
		
		        <html:submit>Upload</html:submit>
		    </html:form>
		    
	    </div>
	</div>    


    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>