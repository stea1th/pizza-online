import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-to-cart-form',
  templateUrl: './add-to-cart-form.component.html',
  styleUrls: ['./add-to-cart-form.component.css']
})
export class AddToCartFormComponent implements OnInit {

  // @Input('product-id') productId: number;

  @Input('product-cost-list') productCostList: any[];

  formPrice = 'Please choose product size';


  addToCartForm = new FormGroup({
    productCost: new FormControl('', Validators.required),
    quantity: new FormControl('', Validators.required),
  });

  constructor() {
  }


  createRange(n: number) {
    return Array(n);
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const body = {productCostId: this.addToCartForm.value.productCost.id, quantity: this.addToCartForm.value.quantity};
    this.addToCartForm.reset();

    console.log(body);
  }

  onChange(val: any) {

    this.formPrice = val?.price.toFixed(2) + 'Euro';
    console.log(val);
  }
}
