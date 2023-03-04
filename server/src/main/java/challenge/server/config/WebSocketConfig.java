package challenge.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] arr = {"chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam","https://66challenge.shop","https://66challenge-server.store"};
        registry.addEndpoint("/ws/chat").setAllowedOrigins(arr).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/publish"); // @MessageMapping 경로의 맨 앞에 추가 된다.
        registry.enableSimpleBroker("/subscribe"); // 구독 경로. 메시지 브로커는 /sub 접두사가 붙은 클라로 메시지를 전달
    }
}
