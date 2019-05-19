<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>    
<!DOCTYPE html>
<html:html locale="true">
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
    <!-- when /monitoring shows DB down: please restart DB server -->
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-control" content="no-cache, no-store, must-revalidate" />
    <!-- Demo Application for Java Web Security Training Course: see http://www.christian-schneider.net/training.html -->
    <title>Marathon</title>
</head>
<body class="metro">
    
    <%@include file="/WEB-INF/parts/navigation.jsp" %>

    <h2>Marathon</h2>

    <table width="80%">
        <tr>
            <td>
                <img src="<c:url value="images/running.jpg"/>" alt="" border="0" />
            </td>
            <td>
    
			    <logic:iterate id="marathon" name="marathons"> 
			        <html:link page="/showResults.page" paramId="marathon" paramName="marathon" paramProperty="id">
			            Results table <bean:write name="marathon" property="title" />
			        </html:link>
			        <br /> 
			    </logic:iterate> 

                <p />
			    
	    	    <b><html:errors /></b>
			    <html:form action="/searchRunner" method="POST"><%--onsubmit="return validateSearchRunnerForm(this);"--%>
			        Runner search: <html:text property="searchTerm" size="15" styleId="searchField" />
			        <script src="/marathon/js/styleMainPage.js"></script>
			        <html:submit>search</html:submit>
			        <%--<html:javascript formName="SearchRunnerForm" />--%>
			    </html:form>
			    
            </td>
        </tr>
    </table>



    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html:html>
