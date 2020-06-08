<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>

<head>
    <title>Spring Secured Sockets</title>
    <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/styles/socket.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/styles/history.css"/>" rel="stylesheet">

    <script src="<c:url value="/resources/vendor/sockjs/sockjs.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/stomp/stomp.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/angular/angular-route.min.js" />"></script>

    <script src="<c:url value="/resources/scripts/app.js" />"></script>
    <script src="<c:url value="/resources/scripts/services/SocketService.js" />"></script>
    <script src="<c:url value="/resources/scripts/services/History.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/indexController.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/socketController.js" />"></script>
    <script src="<c:url value="/resources/scripts/routes/router.js" />"></script>
</head>

<body ng-controller="socketController">
<c:set var="context" scope="session" value="${pageContext.request.contextPath}"/>
<main>
    <div class="wrapper">
        <%--                <a style="float:right" href="${context}/logout">Logout</a>--%>
        <button style="float:right" id="logoutButton">Logout</button>
        <h2>Security alert notifications</h2>
        <div class="tab">
            <button class="tablinks" onclick="openTab(event, 'liveEvents')" id="liveEventsButton">Live events</button>
            <button class="tablinks" onclick="openTab(event, 'history')" id="historyButton">Show history</button>
        </div>
        <script>document.getElementById("liveEventsButton").click();</script>
        <script>new SocketService().connect();</script>
        <script>
            document.getElementById("logoutButton").onclick = function () {
                document.cookie='JSESSIONID=;expires=Thu; 01 Jan 1970';
                location.href = "${context}/logout";
            };
        </script>
        <!-- Tab content -->
        <div id="liveEvents" class="tabcontent">
            <div id="response"></div>
        </div>
        <div id="history" class="tabcontent">
            <div id="alertsistory"></div>
        </div>
    </div>
</main>

<!-- CSRF Token -->
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</body>

</html>