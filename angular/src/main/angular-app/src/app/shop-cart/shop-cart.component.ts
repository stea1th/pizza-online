import { Component, OnInit } from '@angular/core';
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  productCostList: ProductCostElement[];

  constructor(private data: DataService) { }

  ngOnInit(): void {
    this.getProductsInCart();
  }

  getProductsInCart() {
    this.data.getCartProductCosts().subscribe(data => {
      this.productCostList = data;
      return this.productCostList;
    });
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
