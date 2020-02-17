import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-add-to-cart-form',
  templateUrl: './add-to-cart-form.component.html',
  styleUrls: ['./add-to-cart-form.component.css']
})
export class AddToCartFormComponent implements OnInit {

  @Input('product-id') productId: number;


  addToCartForm = new FormGroup({
    quantity: new FormControl(''),
  });

  constructor() {
  }


  createRange(n: number) {
    return Array(n);
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const body = {productId: this.productId, quantity: this.addToCartForm.value.quantity};
    console.log(body);
  }


}
