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
	<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Marathon</title>
</head>
<%--<body class="metro"> --%>
<body>




    <h2>Here you can change your password</h2>


    
    <html:form action="/secured/updatePassword" method="POST">
    	<input type="hidden" name="secret" value="${sessionScope['SENSITIVE_ACTIONS_TOKEN']}" />
	    <table>
	        <tr>
	            <td>Password:</td>
                <td><input type="password" name="password" size="30" maxlength="50" autocomplete="off" /></td>
	        </tr>
	        <tr>
	            <td>Retype password again:</td>
                <td><input type="password" name="passwordAgain" size="30" maxlength="50" autocomplete="off" /></td>
	        </tr>
	        <tr>
	           <td></td>
	           <td align="right"><html:submit>&nbsp;&nbsp;&nbsp;&nbsp;Change password&nbsp;&nbsp;&nbsp;&nbsp;</html:submit></td>
	        </tr>
	        <tr>
	        	<td colspan="2"><b>${requestScope.UpdateResultMessage}</b></td>
	        </tr>
	    </table>


	    <b><html:errors /></b>
    </html:form>

    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>