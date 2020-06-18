import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductCostElement} from "../../model/product-cost-element";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  @Output() refreshCart = new EventEmitter();

  @Output() openSnackBar = new EventEmitter();

  @Output() createTransactionItems = new EventEmitter();

  @Input() totalPay: string;
  @Input() totalQuantity: number;
  @Input() productCostList: ProductCostElement[];

  constructor() {
  }

  ngOnInit(): void {
    this.refreshElements();
  }

  refreshElements() {
    this.refreshCart.emit();
  }
}

