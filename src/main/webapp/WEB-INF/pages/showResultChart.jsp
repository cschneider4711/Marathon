<%@ page language="java" contentType="image/svg+xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="encode" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>


<svg xmlns="http://www.w3.org/2000/svg" version="1.1" 
	 class="chart" width="200" height="120">
	<script> <%--  type="text/ecmascript" --%>
	  var names = new Array();
	  <logic:iterate id="result" indexId="position" length="3" name="results" property="runs">
	  	names[${position}] = "${fn:escapeXml(result.runner.firstname)} ${fn:escapeXml(result.runner.lastname)}";
	  </logic:iterate>
	  
	  function showName(evt, idx) {
	     evt.target.setAttributeNS(null,"style","fill:blue");
         titleElement = document.getElementById("chartTitle");
	     titleElement.textContent=names[idx];
	  }	  
	  
	  function hideName(evt) {
	     evt.target.setAttributeNS(null,"style","fill:steelblue");
         titleElement = document.getElementById("chartTitle");
	     titleElement.textContent="";
	  }
	</script>
    <logic:iterate id="result" indexId="position" length="3" name="results" property="runs">
	  <g transform="translate(0,${position*20})">
	    
	    <rect width="${result.finishingPixels}" height="19" style="fill:steelblue;" 
			  onmouseover="showName(evt,${position})" onmouseout="hideName(evt)"></rect>
	    
	    <text x="37" y="9.5" dy=".35em" 
	    	  style="fill:white; font:10px sans-serif; text-anchor:end;">${result.finishingTime}</text>

	  </g>
    </logic:iterate>
    <text id="chartTitle" transform="translate(0,90)" font-family="Helvetica,Arial" font-size="12"></text>
</svg>