<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Marathon</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>


    <h2>Edit profile photo</h2>
    
    <b><html:errors /></b>

    
        
    <div class="grid">
	    <div class="row">
	        <div class="span6">
	        		    
					        
				<div class="panel">
				    <div class="panel-header">
				        Upload new profile photo:
				    </div>
				    <div class="panel-content">
				    		
					    <html:form action="/secured/updateRunnerPhoto" method="POST" enctype="multipart/form-data">
						    <html:select property="typeOfImage">
					           <html:option value="PNG">PNG</html:option>
					           <html:option value="JPEG">JPEG</html:option>
					           <html:option value="GIF">GIF</html:option>
					           <html:option value="SVG">SVG</html:option>
						    </html:select>
						    <p/>
					        <html:file property="photoFile"/> 
						    <p/>
					        <html:submit>Upload</html:submit>
					    </html:form>
					    		
				    </div>
				</div>
				
								
	        </div>
	        <div class="span6">	   				
				
		        
				<div class="panel">
				    <div class="panel-header">
				        Or import PNG image from following URL:
				    </div>
				    <div class="panel-content">
									    		
					    <html:form action="/secured/importRunnerPhoto" method="POST">
						    <html:text property="importPhotoFromURL"/>
					        <html:submit>Import</html:submit>
					    </html:form>
					    
				    </div>
				</div>
				

   			</div>
	    </div>
	</div>
					        


    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>