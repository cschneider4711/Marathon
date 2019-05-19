<%
String topic = request.getParameter("topic");
if (topic != null) {
    request.getRequestDispatcher("help/"+topic+".html").forward(request, response);
} else {
	String helpFolderName = request.getServletContext().getRealPath("/help");
	String[] helpTopicFiles = new java.io.File(helpFolderName).list();
	java.util.List<String> helpTopics = new java.util.ArrayList<String>();
	for (String file : helpTopicFiles) {
		if (file.endsWith(".html")) {
			helpTopics.add(file.substring(0,file.length()-5));
		}
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	    <%@include file="/WEB-INF/parts/header.jsp" %>
		<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>
		<title>Online Help</title>
	</head>
	<body class="metro">
	
	The following help topic are available:
	
	<ul>
	  <% for (String helpTopic : helpTopics) { %>
	    <li><a href="help.jsp?topic=<%=helpTopic%>"><%=helpTopic%></a></li>
	  <% } %>
	</ul>
	
	</body>
	</html>
<% } %>