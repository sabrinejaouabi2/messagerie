package com.example.pilote_messagerie.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import com.example.pilote_messagerie.Service.ChatWebSocketHandler;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;

    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .setAllowedOrigins("http://localhost:4200")  // Autoriser Angular à accéder à WebSocket
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // Ajouter l'intercepteur de session si nécessaire
                .withSockJS();  // Activer SockJS pour les navigateurs qui ne supportent pas WebSocket natif
    }

    // Optionnel : Si vous avez besoin de personnaliser les interceptors
    @Bean
    public HttpSessionHandshakeInterceptor handshakeInterceptor() {
        return new HttpSessionHandshakeInterceptor();

    }
}

