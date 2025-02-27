import { Component, OnInit } from '@angular/core';
import { Message } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/Services/auth.service';
import { WebsocketService } from 'src/app/Services/websocket.service';
import { IMessage } from '@stomp/stompjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentUser: any;
  friends: any[] = [];
  selectedFriend: any;
  messages: Message[] = [];
  newMessageContent: string = '';
  private stompClient: any;

  constructor(
    private authService: AuthService,
    private websocketService: WebsocketService
  ) { }
  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      console.log('Utilisateur actuel:', user);
      if (user && user.userId) {
        this.currentUser = user;
        this.loadFriends(user.userId); // Chargez les amis si l'ID utilisateur est valide
      } else {
        console.error('L\'ID de l\'utilisateur est indéfini ou l\'utilisateur n\'est pas connecté.');
      }
    });
  }



  loadFriends(userId: number): void {
    if (userId) {
      this.authService.getFriends(userId).subscribe(friends => {
        this.friends = friends;
      }, error => {
        console.error('Erreur lors de la récupération des amis:', error);
      });
    } else {
      console.error('L\'ID de l\'utilisateur est invalide');
    }
  }

  sendMessage(): void {
    if (this.newMessageContent && this.selectedFriend) {
      // Message sans interface, juste un objet
      const message = {
        sender: this.currentUser,
        receiver: this.selectedFriend,
        content: this.newMessageContent,
        timestamp: new Date().toISOString()
      };
      this.websocketService.sendMessage(message); // Envoie via WebSocket
      this.newMessageContent = ''; // Réinitialiser le champ de texte
    }
  }

  // Méthode pour recevoir les messages via WebSocket
  receiveMessages(): Observable<Message> {
    return this.websocketService.receiveMessages();
  }
}
