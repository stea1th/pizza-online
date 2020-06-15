import {Injectable} from '@angular/core';

import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import * as SockJS from "sockjs-client";
import {filter} from "rxjs/operators";
import {Client, Stomp} from "@stomp/stompjs";


@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client: Client;
  private state: BehaviorSubject<SocketClientState>;
  private stompClient;

  constructor() {
    this.init();
    // this.client = over(new SockJS(environment.api));
    // this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    // this.client.connect({}, () => {
    //   this.state.next(SocketClientState.CONNECTED);
    // });
  }

  public init() {
    let ws = new SockJS(environment.api);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, () => {
      this.state.next(SocketClientState.CONNECTED);
    });
  }

  public connect(): Observable<Client> {
    return new Observable<Client>(observer => {
      this.state.pipe(filter(state => state === SocketClientState.CONNECTED)).subscribe(() => {
        observer.next(this.client);
      });
    });
  }
}

export enum SocketClientState {
  ATTEMPTING, CONNECTED
}
