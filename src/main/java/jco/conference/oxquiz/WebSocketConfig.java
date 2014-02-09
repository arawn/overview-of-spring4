package jco.conference.oxquiz;

import jco.conference.oxquiz.websocket.PlayerEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;

@Configuration
@ComponentScan(basePackages = "jco.conference.oxquiz.websocket")
@EnableAspectJAutoProxy
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
    public PlayerEventListener playerEventListener() {
        return new PlayerEventListener();
    }

}
