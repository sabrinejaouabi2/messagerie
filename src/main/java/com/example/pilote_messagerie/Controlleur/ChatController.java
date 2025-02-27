package com.example.pilote_messagerie.Controlleur;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Service.MessageService;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ChatController {
    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }
    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
      messageService.saveMessage(message);
        return ResponseEntity.ok().build();
    }

      @MessageMapping("/chat/{recipientId}")
    public void sendMessage(@DestinationVariable Long recipientId, @Payload Message message) {
        messageService.saveMessage(message);
        // Logique pour envoyer un message en temps réel via WebSocket
    }

   

}

