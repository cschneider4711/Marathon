<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><% // add anti-browser-cache stuff 
String cache = request.getParameter("cache"), cacheVal = request.getParameter("cacheVal");
if (cache != null && cacheVal != null) response.addHeader(cache, cacheVal);
%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Marathon</title>
</head>
<body class="metro">

    <h2>Create account (* all fields required)</h2>
 
    <html:form action="/createAccount" method="POST">
        <table>
            <tr>
                <td>Username *</td>
                <td><html:text property="username" size="15" maxlength="30" /></td>
            </tr>
            <tr>
                <td>Password *</td>
                <td><html:password property="password" size="15" maxlength="30" /></td>
            </tr>
            <tr>
                <td>First name *</td>
                <td><html:text property="firstname" size="30" maxlength="50" /></td>
            </tr>
            <tr>
                <td>Last name *</td>
                <td><html:text property="lastname" size="30" maxlength="50" /></td>
            </tr>
            <tr>
                <td>Street *</td>
                <td><html:text property="street" size="30" maxlength="50" /></td>
            </tr>
            <tr>
                <td>ZIP + City *</td>
                <td><html:text property="zip" size="10" maxlength="10" /> <html:text property="city" size="30" maxlength="50" /></td>
            </tr>
            <tr>
                <td>Creditcard number (for marathon fee) *</td>
                <td><html:text property="creditcardNumber" size="20" maxlength="19" /></td>
            </tr>
            <tr>
                <td>Date of birth (dd.mm.yyyy) *</td>
                <td><html:text property="dateOfBirth" size="10" maxlength="10" /></td>
            </tr>
            <tr>
                <td></td>
                <td><html:submit>Create Account</html:submit></td>
            </tr>
        </table>

    </html:form>    
    
</body>
</html>