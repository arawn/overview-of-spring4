package jco.conference.oxquiz;

import jco.conference.oxquiz.websocket.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan(basePackages = "jco.conference.oxquiz.websocket")
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/join").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/quiz/");
        registry.setApplicationDestinationPrefixes("/quiz");
    }

    @Override
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new TrackingSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }


    class TrackingSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {

        public TrackingSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
            super(clientInboundChannel, clientOutboundChannel);
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            applicationContext.publishEvent(PlayerEvent.in(session));

            super.afterConnectionEstablished(session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            applicationContext.publishEvent(PlayerEvent.out(session));

            super.afterConnectionClosed(session, closeStatus);
        }

    }

}
