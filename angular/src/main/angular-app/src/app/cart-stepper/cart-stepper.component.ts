import { Component, OnInit } from '@angular/core';
import {format} from "util";

@Component({
  selector: 'app-cart-stepper',
  templateUrl: './cart-stepper.component.html',
  styleUrls: ['./cart-stepper.component.css']
})
export class CartStepperComponent implements OnInit {

  isLinear = true;
  orderDateTime: string;

  constructor() { }

  ngOnInit(): void {
  }

  setOrderDateTime(event) {
    this.orderDateTime = event.dayOfMonth + '.' + event.monthValue + '.' + event.year + ' ' + event.hour + ':' + event.minute;
  }

}
