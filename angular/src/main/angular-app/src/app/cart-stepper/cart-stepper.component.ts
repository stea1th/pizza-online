import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {DataService} from "../service/data.service";
import {ProductCostElement} from "./shop-cart/shop-cart.component";
import {Creator} from "../helper/creator";
import {SidenavResponsiveComponent} from "../sidenav-responsive/sidenav-responsive.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-cart-stepper',
  templateUrl: './cart-stepper.component.html',
  styleUrls: ['./cart-stepper.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CartStepperComponent implements OnInit {

  isLinear = true;
  orderDateTime: string;
  productCostList: ProductCostElement[];
  totalPay: string;
  totalQuantity: number;

  constructor(private _data: DataService, private _sideNav: SidenavResponsiveComponent) {
  }

  ngOnInit(): void {
  }

  setOrderDateTime(event) {
    this.orderDateTime = this.attachZero(event.dayOfMonth) + '.'
      + this.attachZero(event.monthValue) + '.'
      + event.year + ' '
      + this.attachZero(event.hour) + ':'
      + this.attachZero(event.minute);
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
    for (let i = 0; i < array.length; i++) {
      sum += array[i].quantity;
    }
    return sum;
  }

  attachZero(num: number): string {
    const str = '' + num;
    return str.length == 1 ? '0' + str : str;
  }

  // openSnackBar(message: string) {
  //   this._snackBar.open(message);
  // }
}

