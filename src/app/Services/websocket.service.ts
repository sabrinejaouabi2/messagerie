import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client, Message } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private client: Client;
  private connectedSubject: Subject<boolean> = new Subject();
  private messageSubject: Subject<Message> = new Subject();

  constructor() {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws/chat'),
      connectHeaders: {},
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('✅ Connecté au WebSocket');
        this.connectedSubject.next(true);
      },
      onDisconnect: () => {
        console.log('❌ Déconnecté du WebSocket');
        this.connectedSubject.next(false);
        this.reconnect();
      },
      onStompError: (frame) => {
        console.error('Erreur STOMP:', frame);
        this.reconnect();
      },
      onWebSocketError: (event) => {
        console.error('Erreur WebSocket:', event);
      }
    });

    this.client.activate(); // Démarre la connexion WebSocket
  }

  private reconnect() {
    console.log('🔄 Tentative de reconnexion...');
    setTimeout(() => {
      this.client.activate();
    }, 5000);
  }

  isConnected(): Observable<boolean> {
    return this.connectedSubject.asObservable();
  }
  // Envoie un message seulement si la connexion est établie
  sendMessage(message: any): void {  // Ici, on n'utilise pas d'interface, juste un type générique 'any'
    this.isConnected().subscribe(isConnected => {
      if (isConnected && this.client.connected) {
        this.client.publish({
          destination: '/app/send-message',
          body: JSON.stringify(message)
        });
        console.log('📩 Message envoyé:', message);
      } else {
        console.log('🔴 WebSocket n\'est pas encore connecté, veuillez réessayer plus tard.');
      }
    });
  }

  // Reçoit des messages via WebSocket
  receiveMessages(): Observable<Message> {
    return this.messageSubject.asObservable();
  }

  // Méthode d'abonnement pour recevoir les messages en temps réel
  subscribeToMessages() {
    this.client.subscribe('/topic/chat', (message: Message) => {
      this.messageSubject.next(message);
    });
  }
}
