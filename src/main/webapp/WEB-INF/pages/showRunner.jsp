<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="encode" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Runner ${runner.firstname} ${runner.lastname}</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>

	
	
	
    <div class="grid">
	    <div class="row">
	        <div class="span6">
	        
				        	
				<div class="panel">
				    <div class="panel-header">
				        Runner <bean:write name="runner" property="firstname" /> <bean:write name="runner" property="lastname" />
				    </div>
				    <div class="panel-content">
				        <bean:write name="runner" property="street" /><br/>
				        <bean:write name="runner" property="zip" /> <bean:write name="runner" property="city" /><br/>
				    </div>
				</div>
				
   
	        </div>
	        <div class="span6">

			    <logic:notEmpty name="runner" property="photoName">
			        <img src="<c:url value="PhotoLoader?photo=${fn:escapeXml(runner.photoName)}"/>" border="0" />
			    </logic:notEmpty>

   			</div>
	    </div>
	</div>


    
    <logic:present role="administrator">
        <html:link href="/marathon/secured/profile.page" paramId="runnerId" paramName="runner" paramProperty="id">Edit runner</html:link>
    </logic:present>

    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>