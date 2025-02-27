import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebsocketService } from 'src/app/Services/websocket.service';
import { Subscription } from 'rxjs'; // Importer Subscription pour gérer l'abonnement

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent  {
  public message: string = '';
  public messages: string[] = [];

  constructor(private WebsocketService: WebsocketService) {
    this.WebsocketService.receiveMessages().subscribe((message) => {
      this.messages.push(message.body);
    });
  }
/*
  sendMessage(): void {
    if (this.message.trim()) {
      this.WebsocketService.sendMessage(this.message);
      this.message = ''; // Clear the input field after sending
    }
  }
    */
}
