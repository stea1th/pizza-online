import {Injectable, OnDestroy} from '@angular/core';

import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import * as SockJS from "sockjs-client";
import {filter, first} from "rxjs/operators";
import {Client, Stomp} from "@stomp/stompjs";


@Injectable({
  providedIn: 'root'
})
export class WebSocketService implements OnDestroy {

  private ws: WebSocket;
  private state: BehaviorSubject<SocketClientState>;
  private stompClient;

  constructor() {
    this.init();
  }

  public init() {
    this.ws = new SockJS(environment.api);
    this.stompClient = Stomp.over(this.ws);
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe("/topic/greetings", (data) => {
        console.log(data);
      })
      this.state.next(SocketClientState.CONNECTED);
    });
  }

  // public connect(): Observable<Client> {
  //   return new Observable<Client>(observer => {
  //     this.state.pipe(filter(state => state === SocketClientState.CONNECTED)).subscribe(() => {
  //       observer.next(this.client);
  //     });
  //   });
  // }

  ngOnDestroy(): void {
    this.close();
  }

  private close() {
    if(this.stompClient) {
      this.ws.close();
      this.stompClient.unsubscribe("/topic/greetings")
    }
  }



  // public send(message: string) {
  //   this.stompClient.send(message);
  // }
}

export enum SocketClientState {
  ATTEMPTING, CONNECTED
}
