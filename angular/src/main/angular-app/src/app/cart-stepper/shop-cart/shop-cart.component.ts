import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Creator} from "../../helper/creator";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  @Output() refreshCart = new EventEmitter();

  @Output() openSnackBar = new EventEmitter();

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

export class ProductCostElement {
  id: number;
  property: string;
  price: number;
  discount: number;
  quantity: number;
  product: any;
}
