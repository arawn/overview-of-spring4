package jco.conference.oxquiz.websocket;

import jco.conference.oxquiz.model.Player;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class PlayerEvent extends ApplicationEvent {

    static enum PlayerEventState { CONNECTED, IN, OUT }


    private PlayerEventState state;

    private PlayerEvent(WebSocketSession session, PlayerEventState state) {
        super(session);
        this.state = state;
    }

    public PlayerEventState getState() {
        return state;
    }

    public Player getPlayer() {
        return (Player) ((WebSocketSession) getSource()).getPrincipal();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlayerEvent{");
        sb.append("state=").append(state).append(", ");
        sb.append("entry=").append(getPlayer()).append('}');
        return sb.toString();
    }

    public static PlayerEvent in(WebSocketSession session) {
        return new PlayerEvent(session, PlayerEventState.IN);
    }

    public static PlayerEvent out(WebSocketSession session) {
        return new PlayerEvent(session, PlayerEventState.OUT);
    }

}
