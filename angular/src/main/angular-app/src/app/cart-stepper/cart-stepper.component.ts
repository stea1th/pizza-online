import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {DataService} from "../service/data.service";
import {ProductCostElement} from "./shop-cart/shop-cart.component";
import {Creator} from "../helper/creator";
import {SidenavResponsiveComponent} from "../sidenav-responsive/sidenav-responsive.component";
import {MatStepper} from "@angular/material/stepper";
import {
  PaypalPaymentComponent,
  TransactionItem,
  UnitAmount
} from "./person-details/paypal-payment/paypal-payment.component";
import {PersonDetailsComponent} from "./person-details/person-details.component";
import {SpinnerService} from "../service/spinner.service";

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
  transactionItems: TransactionItem[];

  @ViewChild('stepper') stepper: MatStepper;

  @ViewChild(PersonDetailsComponent) personDetails: PersonDetailsComponent;

  constructor(private _data: DataService,
              private _sideNav: SidenavResponsiveComponent,
              private _spinner: SpinnerService) {
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
    this._spinner.showSpinner();
    this._data.getCartProductCosts().subscribe(data => {
      console.log(data);
      this.productCostList = data;
      this._sideNav.cart = this.sumAllQuantitiesInCart(data);
      this.createTotal(this.productCostList);
      this._spinner.stopSpinner();
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

  goForward() {
    this.stepper.next();
  }

  createTransactionItems() {
    this.transactionItems = this.productCostList.map(function (element) {
      let amount = new UnitAmount();
      amount.currency_code = 'EUR';
      amount.value = (element.price - element.price * element.discount / 100).toFixed(2);
      let item = new TransactionItem();
      item.name = element.product.name;
      item.unit_amount = amount;
      item.quantity = element.quantity + '';
      return item;
    });
    this.personDetails.paypalPayment.initConfig(this.totalPay, this.transactionItems);
  }
}

