package com.example.pilote_messagerie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pilote_messagerie.Entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);

}
