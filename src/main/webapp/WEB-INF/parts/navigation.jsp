<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<link href="/marathon/css/style.css" rel="stylesheet" type="text/css"/>

<c:url var="profile" value="/secured/profile.page">
</c:url>                        

<%String navClass="blue";%>
<logic:present role="runner">
  <table border="0" width="100%">
  <%navClass="yellow";%>
</logic:present>
<logic:present role="administrator">
  <table border="0" width="100%">
  <%navClass="red";%>
</logic:present>
<logic:notPresent role="runner,administrator">
  <table border="0" width="100%">
</logic:notPresent>

    <tr class="<%=navClass%> navText">
        <td class="<%=navClass%> navText">
            <c:url var="showMarathons" value="/showMarathons.page">
            </c:url>                     
            <a href="${showMarathons}" class="button">Home</a>

            <logic:present role="administrator">
                <c:url var="showUnregistered" value="/admin/showUnregistered.page"></c:url>
                <a href="${showUnregistered}" class="button">Unregistered</a>
            </logic:present>

            <logic:present role="runner">
	            <a href="${profile}" class="button">Profile</a>     
                <c:url var="attendances" value="/secured/attendances.page">
                </c:url>                          
	            <a href="${attendances}" class="button">Attendances</a>     
            </logic:present>    
        </td>
        <td align="right" class="<%=navClass%> navText">
            <logic:present role="runner,administrator">
            	Welcome <b><%= request.getUserPrincipal().getName() %></b> <%-- ${pageContext.request.userPrincipal.name} --%>

                <c:url var="changePassword" value="/secured/editPassword.page" />
	            <a href="${changePassword}" class="button">Change Password</a>     

                <c:url var="logout" value="/logout.page" />
	            <a href="${logout}" class="button">Logout</a>     
            </logic:present>    
            <logic:notPresent role="runner,administrator">
	            <c:url var="register" value="/register.jsp">
                   <c:param name="cache" value="Pragma" />
                   <c:param name="cacheVal" value="no-cache" />
	            </c:url>                          
	            <a href="${register}" class="button">Create Account</a>     

	            <a href="${profile}" class="button">Login</a>     
            </logic:notPresent>    
        </td>
    </tr>
</table>


