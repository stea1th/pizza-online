import { Component, OnInit } from '@angular/core';
import {format} from "util";
import {DataService} from "../service/data.service";
import {ProductCostElement} from "./shop-cart/shop-cart.component";
import {Creator} from "../helper/creator";
import {SidenavResponsiveComponent} from "../sidenav-responsive/sidenav-responsive.component";

@Component({
  selector: 'app-cart-stepper',
  templateUrl: './cart-stepper.component.html',
  styleUrls: ['./cart-stepper.component.css']
})
export class CartStepperComponent implements OnInit {

  isLinear = true;
  orderDateTime: string;
  productCostList: ProductCostElement[];
  totalPay: string;
  totalQuantity: number;

  constructor(private _data: DataService, private _sideNav: SidenavResponsiveComponent) { }

  ngOnInit(): void {
  }

  setOrderDateTime(event) {
    this.orderDateTime = event.dayOfMonth + '.' + event.monthValue + '.' + event.year + ' ' + event.hour + ':' + event.minute;
  }

  getProductsInCart() {
    this._data.getCartProductCosts().subscribe(data => {
      this.productCostList = data;
      this._sideNav.cart = this.sumAllQuantitiesInCart(data);
      this.createTotal(this.productCostList);
      return this.productCostList;
    });
  }

  createTotal(array: ProductCostElement[]) {
    let totalPrice = 0;
    this.totalQuantity = 0;
    for (let i = 0; i < array.length; i++) {
      const price = array[i].price;
      const quantity = array[i].quantity;
      totalPrice += (price - price * array[i].discount / 100) * quantity;
      this.totalQuantity += quantity;
    }
    this.totalPay = Creator.createPrice(totalPrice);
  }

  sumAllQuantitiesInCart(array: ProductCostElement[]) {
    let sum = 0;
    for (let i = 0; i <array.length ; i++) {
      sum += array[i].quantity;
    }
    return sum;
  }

}

