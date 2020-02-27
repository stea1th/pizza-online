import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cart-stepper',
  templateUrl: './cart-stepper.component.html',
  styleUrls: ['./cart-stepper.component.css']
})
export class CartStepperComponent implements OnInit {

  isLinear = true;

  constructor() { }

  ngOnInit(): void {
  }

}
