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
 private final FriendRepository friendRepository;
    private final MessageRepository messageRepository;

    public MessageService(UserRepository userRepository, FriendRepository friendRepository, MessageRepository messageRepository) {
      this.friendRepository = friendRepository;
      this.messageRepository = messageRepository;
  }


// Récupérer la liste des amis d'un utilisateur en excluant l'utilisateur lui-même
public List<User> getFriends(Long userId) {
    // Récupérer les amis où l'utilisateur est 'user'
    List<Friend> friendsAsUser = friendRepository.findByUserUserId(userId); // Utilisateur comme 'user'

    // Fusionner les deux listes d'amis et exclure l'utilisateur lui-même
    return friendsAsUser.stream()
            .map(friend -> friend.getFriend())  // Récupérer le 'friend' de l'entité Friend
            .filter(friend -> !friend.getUserId().equals(userId))  // Exclure l'utilisateur lui-même
            .collect(Collectors.toList());
}




   
    // Enregistrer un message dans la base de données
    public void saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }


}
