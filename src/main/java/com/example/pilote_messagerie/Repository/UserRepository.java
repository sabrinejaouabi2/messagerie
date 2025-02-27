package com.example.pilote_messagerie.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pilote_messagerie.Entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
   // Recherche d'un utilisateur par son nom d'utilisateur
   Optional<User> findByUsername(String username);
    // Rechercher tous les utilisateurs
    List<User> findAll();
    // Trouver les amis de l'utilisateur par son ID
    List<User> findFriendsByUserId(Long userId);

}
