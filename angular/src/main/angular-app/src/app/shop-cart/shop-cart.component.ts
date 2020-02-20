import { Component, OnInit } from '@angular/core';
import {DataService} from "../service/data.service";
import {ProductElement} from "../dto/product-element";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  productList: ProductElement[];

  constructor(private data: DataService) { }

  ngOnInit(): void {
  }

  getProductsInCart() {
    this.data.getCartProducts().subscribe(data => {
      return this.productList = data;
    });
  }

}
