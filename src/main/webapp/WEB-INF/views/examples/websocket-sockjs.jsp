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
<h1>Example: Spring Websocket with SockJS Fallback</h1>
<hr/>
<input id="message" type="text" size="40"/><button onclick="sendMessage();">SEND</button>
<hr/>
<h3>Log: </h3>
<div id="output"></div>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<script language="javascript" type="text/javascript">
    var message = document.getElementById("message");
    var output = document.getElementById("output");

    var echoUri = "/example/websocket/echo/sockjs";
    var sock = new SockJS(echoUri);
    sock.onopen = function(evt) {
        writeToScreen("CONNECTED");
    };
    sock.onclose = function(evt) {
        writeToScreen("DISCONNECTED");
    };
    sock.onmessage = function(evt) {
        writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
    };
    sock.onerror = function(evt) {
        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
    };

    function sendMessage() {
        writeToScreen("SENT: " + message.value);
        sock.send(message.value);
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