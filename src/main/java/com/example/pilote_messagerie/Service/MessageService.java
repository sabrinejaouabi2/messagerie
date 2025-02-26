package com.example.pilote_messagerie.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessages(String sender, String receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }


}
