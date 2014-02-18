package jco.conference.oxquiz;

import jco.conference.oxquiz.handler.PlayersEventSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

@Configuration
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/join").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/quiz/");
        registry.setApplicationDestinationPrefixes("/quiz");
    }

    @Bean
    public PlayersEventSender playersEventSender() {
        return new PlayersEventSender();
    }

    @Override
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new TrackingSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel(), playersEventSender());
    }


    static class TrackingSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {

        private PlayersEventSender playersEventSender;

        public TrackingSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel, PlayersEventSender playersEventSender) {
            super(clientInboundChannel, clientOutboundChannel);
            this.playersEventSender = playersEventSender;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
            this.playersEventSender.publishPlayerInEvent(session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            super.afterConnectionClosed(session, closeStatus);
            this.playersEventSender.publishPlayerOutEvent(session);
        }

    }

}
