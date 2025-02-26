package com.example.pilote_messagerie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pilote_messagerie.Entity.Message;
import com.example.pilote_messagerie.Entity.User;

public interface MessageRepository  extends JpaRepository<Message, Long> {
  List<Message> findBySenderAndReceiver(User sender, User receiver);
}


