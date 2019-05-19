<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Marathon</title>
</head>
<body class="metro">

    <h2>Confirmation</h2>
 
    <html:form action="/createAccount" method="POST">
    	<html:hidden property="confirmed" value="true"/>
    	<html:hidden property="username"/>
    	<html:hidden property="password"/>
    	<html:hidden property="firstname"/>
    	<html:hidden property="lastname"/>
    	<html:hidden property="street"/>
    	<html:hidden property="zip"/>
    	<html:hidden property="city"/>
    	<html:hidden property="creditcardNumber"/>
    	<html:hidden property="dateOfBirth"/>
    	
		    
		    
    <div class="grid">
	    <div class="row">
	        <div class="span6">
	        		    
					        
				<div class="panel">
				    <div class="panel-header">
				        Please review your account creation:
				    </div>
				    <div class="panel-content">
		    	
				        <table>
				            <tr>
				                <td class="right">Username:</td>
				                <td><b><bean:write name="CreateAccountForm" property="username"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">First name:</td>
				                <td><b><bean:write name="CreateAccountForm" property="firstname"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">Last name:</td>
				                <td><b><bean:write name="CreateAccountForm" property="lastname"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">Street:</td>
				                <td><b><bean:write name="CreateAccountForm" property="street"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">ZIP + City:</td>
				                <td><b><bean:write name="CreateAccountForm" property="zip"/> <bean:write name="CreateAccountForm" property="city"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">Creditcard</td>
				                <td><b><bean:write name="CreateAccountForm" property="creditcardNumber"/></b></td>
				            </tr>
				            <tr>
				                <td class="right">Date of birth:</td>
				                <td><b><bean:write name="CreateAccountForm" property="dateOfBirth"/></b></td>
				            </tr>
				            <tr>
				                <td></td>
				                <td class="rightNoPadding"><html:submit>Confirm my data & Login</html:submit></td>
				            </tr>
				        </table>
		
				    </div>
				</div>
				
				
	        </div>
	        <div class="span6">	   				
				
				<div class="notice fg-white">
					If the data is correct, you can create your account by clicking the following button or use your
					browser's back button to edit the data.
				</div>		

   			</div>
	    </div>
	</div>


        
    </html:form>    
    
</body>
</html>