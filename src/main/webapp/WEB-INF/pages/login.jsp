<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Login</title>
</head> 
<body class="metro">
    <h2>Marathon-Login</h2>
    
    <c:url var="j_security_check" value="j_security_check"></c:url>
    <form action="${j_security_check}" method="post">
	    <table>
	        <tr>
	            <td>Username</td>
	            <td><input type="text" name="j_username" value="${cookie.marathonUserLoginBoxDefault.value}" /></td>
	        </tr>
	        <tr>
	            <td>Password</td>
	            <td><input type="password" name="j_password" /></td>
	        </tr>
	        <tr>
	           <td></td>
	           <td align="right"><input type="submit" value="Login" /></td>
	        </tr>
	    </table>
    </form>
        
    
</body>
</html>