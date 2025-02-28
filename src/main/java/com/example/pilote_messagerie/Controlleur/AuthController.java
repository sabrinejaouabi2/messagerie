package com.example.pilote_messagerie.Controlleur;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pilote_messagerie.Entity.User;
import com.example.pilote_messagerie.Service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes depuis localhost:4200

public class AuthController {
      private final UserService userService;
      private final PasswordEncoder passwordEncoder;


      public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
@PostMapping("/register")
public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
    userService.register(user);
    Map<String, String> response = new HashMap<>();
    response.put("message", "User registered successfully: " + user.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


@PostMapping("/login")
public ResponseEntity<Object> login(@RequestBody User user) {
    Optional<User> existingUser = userService.findByUsername(user.getUsername());
    if (existingUser.isPresent() && 
        passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
        
        // Créer un objet de réponse JSON
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Connexion réussie");
        response.put("userId", existingUser.get().getUserId());  // Ajout de l'ID utilisateur
        response.put("username", existingUser.get().getUsername());  // Ajout du nom d'utilisateur si nécessaire
        
        return ResponseEntity.ok(response);  // Retourne un JSON avec message, userId et username
    }
    
    // Réponse pour échec de la connexion
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", "Échec de connexion");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);  // Retourne un JSON
}


 

@GetMapping("/currentUser")
public User getCurrentUser(@RequestParam String username) {
    return userService.getCurrentUser(username);  // Appel de la méthode dans le service
}

@GetMapping("/users")
public List<User> getAllUsers(@RequestParam Long loggedInUserId) {
    return userService.getAllUsersExceptLoggedIn(loggedInUserId);
}
 
}
