import {Component, Input, OnInit} from '@angular/core';
import {ProductCostElement} from "../shop-cart.component";
import {FormControl, Validators} from "@angular/forms";


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input("product-element") productCostElement: ProductCostElement;
  quantity: string;
  quantitySelect;

  constructor() { }

  ngOnInit(): void {
    console.log(this.productCostElement);
    this.quantity = '' + this.productCostElement.quantity;
    this.quantitySelect = new FormControl(this.quantity, Validators.required);
  }

  createRange(n: number) {
    return Array(n);
  }

}
