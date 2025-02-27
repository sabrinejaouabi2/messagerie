package com.example.pilote_messagerie.Entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    private String username;
    private String password;

    // Relation avec les messages envoyés, basée sur la propriété sender dans Message
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
    
    // Relation avec les messages reçus, basée sur la propriété receiver dans Message
    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;
}
