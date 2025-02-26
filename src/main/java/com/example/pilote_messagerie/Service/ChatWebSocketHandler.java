package com.example.pilote_messagerie.Service;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component

public class ChatWebSocketHandler extends TextWebSocketHandler  {
 @Override
 public void afterConnectionEstablished(WebSocketSession session) throws Exception {
     // Code exécuté après la connexion WebSocket
     System.out.println("Nouvelle connexion WebSocket: " + session.getId());
 }

 @Override
 protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
     // Code pour traiter les messages entrants
     System.out.println("Message reçu: " + message.getPayload());

     // Envoi de la réponse au client
     session.sendMessage(new TextMessage("Message reçu: " + message.getPayload()));
 }

 @Override
 public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
     // Code exécuté après la fermeture de la connexion WebSocket
     System.out.println("Connexion WebSocket fermée: " + session.getId());
 }
}
