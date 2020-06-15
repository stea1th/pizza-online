import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {WebSocketService} from "../../service/web-socket.service";

@Component({
  selector: 'app-opened-orders',
  templateUrl: './opened-orders.component.html',
  styleUrls: ['./opened-orders.component.css']
})
export class OpenedOrdersComponent implements OnInit {

  testForm: any;

  constructor(private _websocket: WebSocketService) { }

  ngOnInit(): void {
    this.testForm = new FormGroup({
      firstName: new FormControl('')
    });
  }

  onSubmit() {
    // this._websocket.sendMessage(this.testForm.value.firstName);
    this._websocket.connect();
  }
}
