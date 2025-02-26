import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebsocketService } from 'src/app/Services/websocket.service';
import { Subscription } from 'rxjs'; // Importer Subscription pour gérer l'abonnement

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: string[] = [];
  message = '';
  user: string = 'User1'; // Par exemple, un utilisateur de chat
  private messageSubscription: Subscription; // Déclarer une variable pour gérer l'abonnement

  constructor(private webSocketService: WebsocketService) {}

  ngOnInit() {
    // Se connecter à WebSocket
    this.webSocketService.connect();

    // S'abonner pour recevoir des messages via l'observable
    this.messageSubscription = this.webSocketService.getMessages().subscribe((message: string) => {
      this.messages.push(message);
    });
  }

  // Envoie un message via WebSocket
  sendMessage() {
    if (this.message.trim()) {
      this.webSocketService.sendMessage(this.message);
      this.message = ''; // Réinitialise le champ du message
    }
  }

  ngOnDestroy() {
    // Déconnecte WebSocket et désabonne l'observable lors de la fermeture du composant
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe(); // Se désabonner pour éviter les fuites de mémoire
    }
    this.webSocketService.disconnect();
  }
}
