package jco.conference.example.websocket;

import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EchoHandler implements WebSocketHandler {

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            echoTextMessage((TextMessage) message);
        } else {
            try {
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason(message.toString() + " not supported"));
            }
            catch (IOException ignore) { }
        }
    }

    private void echoTextMessage(TextMessage message) {
        sessions.forEach(session -> {
            try { session.sendMessage(message); }
            catch (IOException ignore) { }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
