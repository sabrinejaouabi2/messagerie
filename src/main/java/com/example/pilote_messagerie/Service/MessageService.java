package com.example.pilote_messagerie.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.pilote_messagerie.Entity.Friend;
import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Entity.User;
import com.example.pilote_messagerie.Repository.FriendRepository;
import com.example.pilote_messagerie.Repository.MessageRepository;
import com.example.pilote_messagerie.Repository.UserRepository;

@Service
public class MessageService {
 private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final MessageRepository messageRepository;

    public MessageService(UserRepository userRepository, FriendRepository friendRepository, MessageRepository messageRepository) {
      this.userRepository = userRepository;
      this.friendRepository = friendRepository;
      this.messageRepository = messageRepository;
  }

   // Récupérer la liste des amis d'un utilisateur en excluant l'utilisateur lui-même
    public List<User> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);
        return friends.stream()
                      .map(friend -> friend.getFriend())
                      .filter(friend -> !friend.getId().equals(userId))  // Exclure l'utilisateur lui-même
                      .collect(Collectors.toList());
    }

    // Récupérer l'historique des messages entre deux utilisateurs
    public List<Message> getMessageHistory(User sender, User receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    // Enregistrer un message dans la base de données
    public void saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }


}
