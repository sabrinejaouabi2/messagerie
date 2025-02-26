package com.example.pilote_messagerie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pilote_messagerie.Entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(String sender, String receiver);
}


