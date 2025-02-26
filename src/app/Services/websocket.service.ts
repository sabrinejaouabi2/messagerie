import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: Client;
  private readonly SOCKET_URL = 'http://localhost:8080/ws/chat';
  private messagesSubject: Subject<string> = new Subject<string>();
  private connected: boolean = false;

  constructor() { }

  // Se connecter à WebSocket
  connect() {
    const socket = new SockJS(this.SOCKET_URL);
    this.stompClient = new Client({
      brokerURL: this.SOCKET_URL,
      connectHeaders: {},
      debug: (str) => console.log('STOMP Debug:', str),
      reconnectDelay: 5000,
      onConnect: (frame) => {
        console.log('WebSocket connected: ' + frame);
        this.connected = true;
        this.subscribeToMessages(); // S'abonner après la connexion
      },
      onDisconnect: () => {
        console.log('WebSocket disconnected');
        this.connected = false;
      },
      onStompError: (frame) => {
        console.error('STOMP Error:', frame);
      }
    });
    this.stompClient.activate();
  }

  // Envoyer un message au serveur WebSocket
  sendMessage(message: string) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/chat',
        body: message,
      });
    } else {
      console.error('WebSocket is not connected');
    }
  }

  // Méthode pour s'abonner aux messages
  private subscribeToMessages() {
    if (this.stompClient && this.connected) {
      this.stompClient.subscribe('/topic/chat', (message: Message) => {
        this.messagesSubject.next(message.body);
      });
    }
  }

  // Retourne un observable pour recevoir les messages
  getMessages() {
    return this.messagesSubject.asObservable();
  }

  // Déconnexion du WebSocket
  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}
