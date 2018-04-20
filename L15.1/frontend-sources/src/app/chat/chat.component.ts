import { Component, OnInit } from '@angular/core';
import { Message } from '../message';
import { AuthMessage } from '../authMessage';
import { RegisterMessage } from '../registerMessage';
import { LoginMessage} from '../loginMessage';
import { ChatMessage } from '../chatMessage';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private ws: WebSocket;
  user: string = "";
  users: string[];
  messages: Message[] = [];

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.messageService.createObservableSocket("ws://localhost:3000/messages")
      .subscribe(str => {
        console.log('Message received: ' + str);
        let message = JSON.parse(str);
        if (message.type === "chat" || message.type === "info") {
          this.messages.push(message);
        } else if (message.type === "users") {
          this.users = message.users;
        } else if (message.type === "auth" && message.success) {
          this.user = message.user;
        }
      });
  }

  login(username: string, password: string): void {
    let msg = new LoginMessage();
    msg.username = username;
    msg.password = password;
    this.sendMessage(JSON.stringify(msg));
  }

  register(username: string, password: string, rpassword: string): void {
    if (password === rpassword) {
      let msg = new RegisterMessage();
      msg.username = username;
      msg.password = password;
      this.sendMessage(JSON.stringify(msg));
    }
  }

  sendChatMessage(text: string): void {
    let msg = new ChatMessage();
    msg.from = this.user;
    msg.to = "";
    msg.text = text;
    this.sendMessage(JSON.stringify(msg));
  }

  private sendMessage(message: string): void {
    console.log('About to send message: ' + message);
    this.messageService.sendMessage(message);
  }
}
