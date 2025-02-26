package com.example.pilote_messagerie.Controlleur;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Service.MessageService;
@RestController
@RequestMapping("/api/messages")
public class ChatController {
    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.saveMessage(message));
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String sender, @RequestParam String receiver) {
        return ResponseEntity.ok(messageService.getMessages(sender, receiver));
    }
    
}