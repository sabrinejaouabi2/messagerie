import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Client, Message } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: Client;
  private messagesSubject = new Subject<string>();  // Sujet pour les messages reçus

  constructor() {
    // Initialisation du client STOMP
    this.stompClient = new Client({
      brokerURL: 'http://localhost:8080/ws/chat', // URL du serveur WebSocket
      webSocketFactory: () => new SockJS('http://localhost:8080/ws/chat'), // Connexion via SockJS
      onConnect: () => {
        console.log('WebSocket connecté');
        this.stompClient.subscribe('/topic/messages', (message: Message) => {
          this.messagesSubject.next(message.body);  // Envoi du message reçu au sujet
        });
      },
      onStompError: (frame) => {
        console.error('Erreur STOMP', frame);
      }
    });
  }

  connect(): void {
    this.stompClient.activate();  // Activer la connexion WebSocket
  }

  disconnect(): void {
    if (this.stompClient.active) {
      this.stompClient.deactivate();  // Désactiver la connexion WebSocket
    }
  }

  sendMessage(message: string): void {
    this.stompClient.publish({ destination: '/app/chat', body: message });  // Envoi du message
  }

  getMessages() {
    return this.messagesSubject.asObservable();  // Retourner un observable pour les messages reçus
  }
}
