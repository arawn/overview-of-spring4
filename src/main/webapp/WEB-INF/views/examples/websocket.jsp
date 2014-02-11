<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Overview of Spring 4.0 :: Examples</title>
</head>
<body>
<h1>Example: Spring Websocket</h1>
<hr/>
<input id="message" type="text" size="40"/><button onclick="sendMessage();">SEND</button>
<hr/>
<h3>Log: </h3>
<div id="output"></div>
<script language="javascript" type="text/javascript">
    var message = document.getElementById("message");
    var output = document.getElementById("output");

    var echoUri = "ws://localhost:8080/example/websocket/echo";
    var websocket = new WebSocket(echoUri);

    websocket.onopen = function(evt) {
        writeToScreen("CONNECTED");
    };

    websocket.onclose = function(evt) {
        writeToScreen("DISCONNECTED");
    };

    websocket.onmessage = function(evt) {
        writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
    };

    websocket.onerror = function(evt) {
        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
    };

    function sendMessage() {
        writeToScreen("SENT: " + message.value);
        websocket.send(message.value);
        message.value = "";
    }

    function writeToScreen(message) {
        var pre = document.createElement("p");
        pre.style.wordWrap = "break-word";
        pre.innerHTML = message;
        output.appendChild(pre);
    }
</script>
</body>
</html>