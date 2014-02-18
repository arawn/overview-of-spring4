<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="sf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Overview of Spring 4.0 :: Examples</title>
</head>
<body>
<h3>Environment</h3>
<ul>
    <li>AppName : <sf:eval expression="@environment.getProperty('appName')"/></li>
    <li>AppVersion : <sf:eval expression="@environment.getProperty('appVersion')"/></li>
</ul>
<hr/>
<h3>Examples</h3>
<ul>
    <li><a href='<sf:url value="/example/websocket"/>'>WebSocket</a></li>
    <li><a href='<sf:url value="/example/websocket/sockjs"/>'>WebSocket with SockJS Fallback</a></li>
    <li><a href='<sf:url value="/example/websocket/stomp"/>'>STOMP on WebSocket</a></li>
</ul>
</body>
</html>