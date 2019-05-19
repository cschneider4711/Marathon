<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>L&auml;ufer</title>
    <logic:present role="administrator">
	    <%--<script src="js/jquery-1.10.2.min.js"></script> --%>
	    <script src="/marathon/js/profileDetails.js"></script>
    </logic:present>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>


    <h2>Result of searching for &quot;<%=request.getParameter("searchTerm")%>&quot;</h2>
    
    <p />
    
    
    <div class="grid">
	    <div class="row">
	        <div class="span6">
	
	
			    <logic:iterate id="runner" name="runners"> 
			    	
						<div class="panel">
						    <div class="panel-header">
						       <span class="searchResult" data-runner="${runner.id}">
							       Runner:
							       <html:link page="/showRunner.page" paramId="runner" paramName="runner" paramProperty="id" title="Show profile of ${runner.firstname} ${runner.lastname}">
							           <bean:write name="runner" property="username" />
							       </html:link>
						       </span>
						    </div>
						    <div class="panel-content">
						       <b><bean:write name="runner" property="firstname" /> <bean:write name="runner" property="lastname" /></b>
						       <br />
						       Date of birth: <bean:write name="runner" property="dateOfBirth" />
						    </div>
						</div>
					
					<p/>&nbsp;<p/>
			    </logic:iterate> 
				   
	        </div>
	        <div class="span6">

			    <logic:present role="administrator">
			    	<div class="detailLayer">
			    		<div id="profileDetails" class="notice fg-white">Hover name title to see details</div>
			    	</div>
			    </logic:present>

   			</div>
	    </div>
	</div>
    
    
    
    <p />
    <hr />
    <bean:write name="runnersFound" /> runners found while searching for <bean:write name="SearchRunnerForm" property="searchTerm" />.
    <p />
    
    <c:url var="showMarathons" value="/showMarathons.page"/>
    <a href="${showMarathons}" class="button" title="Leave the result list of your search for ${param.searchTerm}">back</a>     



    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>