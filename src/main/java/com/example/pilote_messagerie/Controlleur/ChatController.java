package com.example.pilote_messagerie.Controlleur;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Entity.User;

import com.example.pilote_messagerie.Service.MessageService;
@RestController
<<<<<<< Updated upstream
@RequestMapping("/api/messages")
=======
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

>>>>>>> Stashed changes
public class ChatController {
    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

   @GetMapping("/friends/{userId}")
    public List<User> getFriends(@PathVariable Long userId) {
        return messageService.getFriends(userId);
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public List<Message> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        User sender = new User();
        sender.setId(senderId);
        User receiver = new User();
        receiver.setId(receiverId);
        return messageService.getMessageHistory(sender, receiver);
    }
<<<<<<< Updated upstream
    
}
=======

    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
      messageService.saveMessage(message);
        return ResponseEntity.ok().build();
    }
}
>>>>>>> Stashed changes
