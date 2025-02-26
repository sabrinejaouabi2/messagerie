package com.example.pilote_messagerie.Service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component // Annotation pour déclarer ce handler comme un bean
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Traitement des messages reçus
        session.sendMessage(new TextMessage("Message reçu: " + message.getPayload()));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Nouvelle connexion WebSocket : " + session.getId());
    }
}
