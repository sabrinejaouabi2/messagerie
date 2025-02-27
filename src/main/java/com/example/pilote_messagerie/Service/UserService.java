package com.example.pilote_messagerie.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pilote_messagerie.Entity.User;
import com.example.pilote_messagerie.Repository.UserRepository;

@Service
public class UserService {
     private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

public User register(User user) {
    System.out.println("Encoding password for user: " + user.getUsername());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    // Log après sauvegarde
    System.out.println("User saved: " + savedUser.getUsername());
    return savedUser;
}

 public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
   

// Méthode pour récupérer un utilisateur par son nom d'utilisateur
public User getCurrentUser(String username) {
    // Utilisation de la méthode findByUsername du repository
    Optional<User> user = userRepository.findByUsername(username);
    return user.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
}

// Service pour récupérer tous les utilisateurs sauf l'utilisateur connecté
public List<User> getAllUsersExceptLoggedIn(Long loggedInUserId) {
    return userRepository.findAll().stream()
        .filter(user -> !user.getUserId().equals(loggedInUserId)) // Exclure l'utilisateur connecté
        .collect(Collectors.toList());
}


 // Récupérer les amis de l'utilisateur connecté
 public List<User> getFriends(Long id) {
    return userRepository.findFriendsByUserId(id);
}

    
}
