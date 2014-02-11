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
<h1>Example: STOMP on Spring Websocket</h1>
<hr/>
<input id="message" type="text" size="40"/><button onclick="sendMessage();">SEND</button>
<hr/>
<h3>Log: </h3>
<div id="output"></div>
<script src="/resources/js/stomp.min.js"></script>
<script language="javascript" type="text/javascript">
    var message = document.getElementById("message");
    var output = document.getElementById("output");

    var echoUri = "ws://localhost:8080/example/websocket/endpoint/stomp";
    var websocket = new WebSocket(echoUri);

    var stompClient = Stomp.over(websocket);
    stompClient.connect(' ', ' ', function(frame) {
        writeToScreen("CONNECTED");

        stompClient.subscribe("/topic/echo", function(message) {
            writeToScreen('<span style="color: blue;">RESPONSE: ' + message.body + '</span>');
        });
    }, function (error) {
        writeToScreen('<span style="color: red;">ERROR:</span> ' + error);
    });

    function sendMessage() {
        writeToScreen("SENT: " + message.value);
        stompClient.send('/stomp/message', {}, message.value);
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