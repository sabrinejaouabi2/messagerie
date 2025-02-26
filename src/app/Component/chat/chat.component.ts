import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebsocketService } from 'src/app/Services/websocket.service';
import { Subscription } from 'rxjs'; // Importer Subscription pour gérer l'abonnement

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  message: string = '';  // Lien avec le champ de message dans le HTML
  messages: string[] = [];  // Liste des messages reçus

  private messagesSubscription: Subscription;  // Abonnement aux messages

  constructor(private webSocketService: WebsocketService) { }

  ngOnInit(): void {
    // Se connecter au serveur WebSocket
    this.webSocketService.connect();

    // S'abonner pour recevoir les messages du serveur WebSocket
    this.messagesSubscription = this.webSocketService.getMessages().subscribe((msg: string) => {
      this.messages.push(msg);  // Ajouter chaque message à la liste
    });
  }

  ngOnDestroy(): void {
    // Se déconnecter et se désabonner lors de la destruction du composant
    this.webSocketService.disconnect();
    if (this.messagesSubscription) {
      this.messagesSubscription.unsubscribe();  // Désabonnement pour éviter les fuites de mémoire
    }
  }

  sendMessage(message: string): void {
    if (message.trim()) {
      // Envoyer un message via WebSocket
      this.webSocketService.sendMessage(message);
      this.message = '';  // Réinitialiser le champ de saisie après envoi
    }
  }
}
