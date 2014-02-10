package jco.conference.oxquiz.websocket;

import jco.conference.oxquiz.model.Player;
import jco.conference.oxquiz.model.repository.PlayerRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketSession;

@Aspect
public class PlayersEventSender implements InitializingBean {

    private SimpMessageSendingOperations messagingTemplate;
    private PlayerRepository playerRepository;


    public void publishPlayerInEvent(WebSocketSession session) {
        PlayerEvent event = PlayerEvent.connected(session);

        playerRepository.save(event.getPlayer());
        messagingTemplate.convertAndSend("/quiz/players/in", event.getPlayer());
    }

    public void publishPlayerOutEvent(WebSocketSession session) {
        PlayerEvent event = PlayerEvent.disconnected(session);

        playerRepository.remove(event.getPlayer());
        messagingTemplate.convertAndSend("/quiz/players/out", event.getPlayer());
    }

    @Autowired
    public void setMessagingTemplate(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messagingTemplate);
        Assert.notNull(this.playerRepository);
    }


    @Around("execution(* org.springframework.web.socket.WebSocketHandler.afterConnectionEstablished(..))")
    public Object publishPlayerInEvent(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object retVal = thisJoinPoint.proceed();
        publishPlayerInEvent((WebSocketSession) thisJoinPoint.getArgs()[0]);
        return retVal;
    }

    @Around("execution(* org.springframework.web.socket.WebSocketHandler.afterConnectionClosed(..))")
    public Object publishPlayerOutEvent(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object retVal = thisJoinPoint.proceed();
        publishPlayerOutEvent((WebSocketSession) thisJoinPoint.getArgs()[0]);
        return retVal;
    }

    static enum PlayerEventType { CONNECTED, DISCONNECTED }
    static class PlayerEvent {

        private WebSocketSession session;
        private PlayerEventType type;

        private PlayerEvent(WebSocketSession session, PlayerEventType type) {
            this.session = session;
            this.type = type;
        }

        public Player getPlayer() {
            return (Player) session.getPrincipal();
        }

        public PlayerEventType getType() {
            return type;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("PlayerEvent{");
            sb.append("type=").append(getType()).append(", ");
            sb.append("player=").append(getPlayer()).append('}');
            return sb.toString();
        }

        static PlayerEvent connected(WebSocketSession session) {
            PlayerEvent event = new PlayerEvent(session, PlayerEventType.CONNECTED);
            event.getPlayer().connect();

            return event;
        }

        static PlayerEvent disconnected(WebSocketSession session) {
            PlayerEvent event = new PlayerEvent(session, PlayerEventType.DISCONNECTED);
            event.getPlayer().disconnect();

            return event;
        }

    }

}
