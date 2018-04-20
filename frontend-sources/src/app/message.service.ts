import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Message } from './message';

@Injectable()
export class MessageService {
  private ws: WebSocket;

  constructor() { }

  createObservableSocket(url: string): Observable<string> {
    this.ws = new WebSocket(url);
    return new Observable(observer => {
      this.ws.onmessage = (event) => observer.next(event.data);
      this.ws.onerror = (event) => observer.error(event);
      this.ws.onclose = (event) => observer.complete();
    })
  }

  sendMessage(message: any) {
    this.ws.send(message);
  }

}
