<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="encode" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
	<title>Marathon Results</title>
</head>
<body class="metro">

    <%@include file="/WEB-INF/parts/navigation.jsp" %>




    <logic:notEmpty name="results" property="marathon">

	    <h2>Marathon Results <bean:write name="results" property="marathon.title" /></h2>
	    <p />


	    <c:choose>
	      <c:when test="${param.refresh != null}">
	        <script>
	          // dynamic refresh interval to allow parameterized refresh rate by caller
	          window.setTimeout('location.reload()', ${fn:escapeXml(param.refresh)});
	        </script>
	      </c:when>
	      <c:otherwise>
	      	Show live results:
	        <a href="/marathon/showResults.page?marathon=${results.marathon.id}&amp;refresh=20000">slow</a>
	         | 
	        <a href="/marathon/showResults.page?marathon=${results.marathon.id}&amp;refresh=5000">quick</a>
	      </c:otherwise>
	    </c:choose>

		<p>&nbsp;</p>
	    
        <logic:present role="administrator">
        	<a href="/marathon/admin/deleteAllResults.page?marathon=${results.marathon.id}&amp;secret=${sessionScope['SENSITIVE_ACTIONS_TOKEN']}">Delete all results</a>
        	<%--
		    <html:link href="/marathon/admin/deleteAllResults.page" paramId="marathon" paramName="results" paramProperty="marathon.id">Delete all results</html:link> --%>
            <p/>
        </logic:present>
	    
	    
	    
	    <table width="98%" border="0">
	    	<tr>
	    		<td valign="top">
			
			
			
			
			
			        <html:form action="/admin/updateResults" method="POST">
			
				        <table border="1" class="profileTable">
				            <tr class="tableHeader">
				                <th>Standings</th>
				                <th>Runner</th>
				                <input type="hidden" 
				                name="marathon" 
				                value="${fn:escapeXml(results.marathon.id)}" />
			                    <th>Time</th>
				            </tr>
				            
				            <logic:iterate id="result" indexId="position" name="results" property="runs">
				            	<% String trClass = position % 2 == 0 ? "white" : "gray"; %>
								<tr class="<%=trClass%>">
				                    <td>
			                            <logic:notPresent role="administrator">
			    	                       ${position + 1}
			   	                        </logic:notPresent>
			                        </td>
				                    <td>
										<c:url var="runnerURL" value="/showRunner.page">
										   <c:param name="runner" value="${result.runner.id}" />
										</c:url>	                       
				                        <a href="${runnerURL}">
				                            ${result.runner.firstname} ${result.runner.lastname}
				                        </a>
				                    </td>
				                    <td>
			                            <logic:present role="administrator">
			                                <input type="text" name="resultsMapped(${result.runner.id})" value="${result.finishingTime}" />
			                            </logic:present>
			                            <logic:notPresent role="administrator">
			                                ${result.finishingTime}
			                            </logic:notPresent>
				                    </td>
				                </tr> 
				            </logic:iterate>
			            
			            </table>
			
			            <logic:present role="administrator">
			                <html:submit>Save</html:submit>
			                <p/>
					        <html:link href="/marathon/admin/editResultsViaImport.page" paramId="marathon" paramName="results" paramProperty="marathon.id">Mass update via import</html:link>
					        <p/>
					        ${requestScope.ImportResultMessage}
			            </logic:present>
			        </html:form>






	    		</td>
	    		
	    		<td valign="top" width="250">
	    		
					<%-- SVG Chart --%>   
					<h3>Top 3 Chart</h3>
					<c:url var="chartURL" value="/showResults.page?marathon=${fn:escapeXml(results.marathon.id)}&type=svg"></c:url>
					<object type="image/svg+xml" data="${chartURL}"></object>
	    			
	    		
	    		</td>
	    	</tr>
	    </table>
	    

    </logic:notEmpty>
    
    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>