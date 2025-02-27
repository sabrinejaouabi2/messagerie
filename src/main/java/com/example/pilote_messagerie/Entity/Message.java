package com.example.pilote_messagerie.Entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;  // Expéditeur du message

    @ManyToOne
    @JoinColumn(name = "receiver_id")  // C'est receiver_id pour le destinataire
    private User receiver;  // Destinataire du message

    private String content;
    private LocalDateTime timestamp;

    // Constructeurs, getters et setters générés par Lombok
}
