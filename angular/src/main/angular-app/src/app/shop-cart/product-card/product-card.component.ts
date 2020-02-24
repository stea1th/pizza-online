import {Component, Input, OnInit} from '@angular/core';
import {ProductCostElement} from "../shop-cart.component";
import {FormControl, Validators} from "@angular/forms";
import {faEuroSign} from "@fortawesome/free-solid-svg-icons";
import {Creator} from "../../helper/creator";


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input("product-element") productCostElement: ProductCostElement;
  formNormalPrice: string;
  quantity: string;
  quantitySelect;
  faEuro = faEuroSign;

  constructor() { }

  ngOnInit(): void {
    this.quantity = '' + this.productCostElement.quantity;
    this.formNormalPrice = Creator.createPrice(this.productCostElement.price);
    this.quantitySelect = new FormControl(this.quantity, Validators.required);
    this.quantitySelect.markAsPristine();
  }

  createRange() {
    return Array(this.productCostElement.quantity + 5);
  }

  onChange(val: any) {
    if(this.quantitySelect.pristine == false) {
      console.log(this.quantitySelect.value);
    }
  }
}
