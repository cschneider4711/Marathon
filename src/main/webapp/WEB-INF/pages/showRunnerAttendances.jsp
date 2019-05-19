<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Attendances of ${runner.firstname} ${runner.lastname}</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>




    <b><html:errors /></b>
    

    <div class="grid">
	    <div class="row">
	        <div class="span6">
	        
				<div class="panel">
				    <div class="panel-header">
				        Marathon Attendances
				    </div>
				    <div class="panel-content">


					    Your are attending the following marathons:<p/>&nbsp;<p/>
					    <html:form action="/secured/updateRunnerAttendances" method="POST">
						    <table>
					            <tr>
					                <th>Marathon</th>
					                <th>Attending?</th>
					            </tr>
					    
					            <logic:iterate id="attendance" name="attendancesList"> 
					                <tr>
					                    <td><bean:write name="attendance" property="marathon.title" /></td>
					                    <td class="center">
											<html:multibox property="selectedMarathons">
					    						<bean:write name="attendance" property="marathon.id"/>
											</html:multibox>
					                    </td>
					                </tr>
					            </logic:iterate>
					            <tr>
					                <td></td>
					                <td class="center"><html:submit>Save</html:submit></td>
					            </tr>
						    </table>

					        
					        <% 
								java.io.ByteArrayOutputStream sink = new java.io.ByteArrayOutputStream();
					        	java.io.ObjectOutputStream output = new java.io.ObjectOutputStream(sink);
								output.writeObject(new java.util.ArrayList<String>());
								output.close();
								String base64 = new String(java.util.Base64.getEncoder().encode(sink.toByteArray()));
					        %>
					        <input type="hidden" name="state" value="<%=base64%>"/>


					    </html:form>

				    </div>
				</div>

	        </div>
	        <div class="span6">	        
				
				<c:if test="${not empty requestScope.UpdateResultMessage}">
					<div class="notice fg-white">
					    ${requestScope.UpdateResultMessage}
					</div>
				</c:if>
				
   			</div>
	    </div>
	</div>


    <p/>


    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>