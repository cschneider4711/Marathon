<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Marathon</title>
</head>
<body>
	<c:url var="startURL" value="showMarathons.page"></c:url>
    <a href="${startURL}">Enter Marathon application</a><p/>
    <script type="text/javascript">
      document.write('You will be forwarded in window '+window.name+' to the <b>Marathon-Application</b>...');
      location.href = '${startURL}';
    </script>
</body>
</html>
