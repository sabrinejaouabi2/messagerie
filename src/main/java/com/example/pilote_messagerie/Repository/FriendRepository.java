package com.example.pilote_messagerie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pilote_messagerie.Entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
     // Trouver les amis de l'utilisateur (user)
     List<Friend> findByUserUserId(Long userId);

     // Trouver les amis où l'utilisateur est l'ami (friend)
     List<Friend> findByFriendUserId(Long friendId);


}
