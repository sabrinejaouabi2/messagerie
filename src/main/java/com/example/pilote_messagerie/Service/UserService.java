package com.example.pilote_messagerie.Service;
import java.util.Optional;
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


    
}
