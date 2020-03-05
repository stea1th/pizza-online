import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

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

  // createTransactionItem() {
  //   this.createTransactionItems.emit();
  // }

  ngOnInit(): void {
    this.refreshElements();
  }

  refreshElements() {
    this.refreshCart.emit();
  }
}

export class ProductCostElement {
  id: number;
  property: string;
  price: number;
  discount: number;
  quantity: number;
  product: any;
}
