package com.example.pilote_messagerie.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.pilote_messagerie.Service.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws/chat")
                .setAllowedOrigins("http://localhost:4200") // Autoriser l'origine Angular
                .withSockJS(); // Activer SockJS si nécessaire
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ChatWebSocketHandler();  // Handler WebSocket personnalisé
    }

    // Configuration CORS pour le WebSocket
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**")
                .allowedOrigins("http://localhost:4200")  // Ajouter l'origine de votre frontend
                .allowedMethods("GET", "POST", "OPTIONS");
    }
}
