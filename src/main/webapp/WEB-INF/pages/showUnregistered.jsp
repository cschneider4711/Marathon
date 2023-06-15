<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>    
<!DOCTYPE html>
<html:html locale="true">
<head>
    <%@include file="/WEB-INF/parts/header.jsp" %>
    <title>Marathon</title>
</head>
<body class="metro">
    
    <%@include file="/WEB-INF/parts/navigation.jsp" %>

    <h2>Unregistered Runners</h2>

    <table id="runnersTable" broder="1" cellpadding="3" cellspacing="1">
        <thead>
        <tr>
            <th>Runner ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>

    <script>
        window.onload = function() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/marathon/rest/runners/unregistered', true);
            xhr.onload = function() {
                if (this.status == 200) {
                    var runners = JSON.parse(this.responseText);
                    var tableBody = document.querySelector("#runnersTable tbody");

                    for (var i = 0; i < runners.length; i++) {
                        var tr = document.createElement('tr');

                        var tdId = document.createElement('td');
                        tdId.textContent = runners[i].id;
                        tr.appendChild(tdId);

                        var tdFirstName = document.createElement('td');
                        tdFirstName.textContent = runners[i].firstname;
                        tr.appendChild(tdFirstName);

                        var tdLastName = document.createElement('td');
                        tdLastName.textContent = runners[i].lastname;
                        tr.appendChild(tdLastName);

                        tableBody.appendChild(tr);
                    }
                } else {
                    console.error('Failed to load unregistered runners:', this.status, this.statusText);
                }
            };
            xhr.onerror = function() {
                console.error('Network error while trying to load unregistered runners');
            };
            xhr.send();
        };
    </script>

    <%@include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html:html>
