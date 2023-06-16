<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Runner ${runner.firstname} ${runner.lastname}</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>

    <%--<h2>Welcome <bean:write name="runner" property="firstname" /> <bean:write name="runner" property="lastname" /></h2>--%>

    <b><html:errors /></b>
   	 

    <div class="grid">
	    <div class="row">
	        <div class="span6">
	        
				<div class="panel">
				    <div class="panel-header">
				        Your profile <i>(all fields required)</i>
				    </div>
				    <div class="panel-content">

					    <html:form action="/secured/updateRunnerProfile" method="POST"><%--onsubmit="return validateRunnerForm(this);"--%>
						    <table>
					            <tr>
					                <td class="right">Username</td>
					                <td><html:text property="username" readonly="true" size="24" maxlength="30" /></td>
					            </tr>
					            <tr>
					                <td class="right">First name</td>
					                <td><html:text property="firstname" size="24" maxlength="45" /></td>
					            </tr>
					            <tr>
					                <td class="right">Last name</td>
					                <td><html:text property="lastname" size="24" maxlength="45" /></td>
					            </tr>
					            <tr>
					                <td class="right">Street</td>
					                <td><html:text property="street" size="24" maxlength="50" /></td>
					            </tr>
					            <tr>
					                <td class="right">ZIP</td>
					                <td><html:text property="zip" size="24" maxlength="10" /></td>
					            </tr>
					            <tr>
					                <td class="right">City</td>
					                <td><html:text property="city" size="24" maxlength="50" /></td>
					            </tr>
								<%--
					            <tr>
					                <td class="right">Creditcard number</td>
					                <td><html:text property="creditcardNumber" size="24" maxlength="19" /></td>
					            </tr>
					            --%>
					            <tr>
					                <td class="right">Date of birth (dd.mm.yyyy)</td>
					                <td><html:text property="dateOfBirth" size="24" maxlength="10" /></td>
					            </tr>
					            <tr>
					                <td></td>
					                <td class="rightNoPadding"><html:submit>Save</html:submit></td>
					            </tr>
						    </table>
						    <p/>
					        <html:hidden property="id"/>
							<%-- <html:javascript formName="RunnerForm" /> --%>

					        <% 
								com.thoughtworks.xstream.XStream xstream = new com.thoughtworks.xstream.XStream();
								String xml = xstream.toXML(new java.util.HashMap());
								String base64 = java.util.Base64.getEncoder().encodeToString(xml.getBytes("UTF-8"));
					        %>
					        <input type="hidden" name="state" value="<%=base64%>"/>

					    </html:form>

				    </div>
				</div>
				
	        </div>
	        <div class="span6">	        
	        
			    <logic:notEmpty name="runner" property="photoName">
			        <img src="<c:url value="../PhotoLoader?photo=${fn:escapeXml(runner.photoName)}"/>" border="0" />
			    </logic:notEmpty>
				
				<c:if test="${not empty requestScope.UpdateResultMessage}">
					<div class="notice fg-white">
					    ${requestScope.UpdateResultMessage}
					</div>
				</c:if>

   			</div>
	    </div>
	</div>





    <p/>
    <logic:present role="runner">
        <%-- Photo only allowed by runner itself not even admin (see roles="runner" constraint work in struts-config) --%>
        <html:link href="/marathon/secured/editRunnerPhoto.page">Edit profile photo</html:link> <p/>
    </logic:present>

    <a href="/marathon/Permalink?act=/marathon/showRunner.page%3frunner%3d${runner.id}">Public runner's profile link (bookmark-safe)</a>

    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>