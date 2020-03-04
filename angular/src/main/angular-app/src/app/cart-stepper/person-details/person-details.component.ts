import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PersonDetails} from "../../person-tabs/person-tabs.component";
import {DataService} from "../../service/data.service";
import {ProductCostElement} from "../shop-cart/shop-cart.component";
import {SidenavResponsiveComponent} from "../../sidenav-responsive/sidenav-responsive.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ICreateOrderRequest, IPayPalConfig, ITransactionItem, IUnitAmount} from "ngx-paypal";

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.css']
})
export class PersonDetailsComponent implements OnInit {
  firstFormGroup: any;
  secondFormGroup: any;
  isLinear = true;
  isDisabled = true;
  personDetails: PersonDetails;
  orderDateTime: OrderDateTime;

  clientId = 'AYKcsiSUWdhRnlVsPq6-aid-OnWm1UqL_dbFw3lsqei_Cz_VLUX6hGeXMrne5I4YsoBQR6ceeVIjVNl-';

  payPalConfig?: IPayPalConfig;

  transactionItems: ITransactionItem[];

  @Output() setOrderDateTime = new EventEmitter();
  @Output() refreshCart = new EventEmitter();

  @Output() goForward = new EventEmitter();


  @Input() productCostList: ProductCostElement[];
  @Input() totalPay: string;

  constructor(private _data: DataService, private _sideNav: SidenavResponsiveComponent, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {

    this._data.getPersonDetails().subscribe(data => {
      this.personDetails = data;
    });
    this.firstFormGroup = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required)
    });
    this.secondFormGroup = new FormGroup({
      street: new FormControl('', Validators.required),
      city: new FormControl('', Validators.required),
      zip: new FormControl('', Validators.required),
      country: new FormControl('', Validators.required)
    });

  }

  saveDetails() {
    if (!this.firstFormGroup.pristine) {
      this.personDetails.firstName = this.firstFormGroup.value.firstName;
      this.personDetails.lastName = this.firstFormGroup.value.lastName;
    }
    if (!this.secondFormGroup.pristine) {
      this.personDetails.address.street = this.secondFormGroup.value.street;
      this.personDetails.address.zip = this.secondFormGroup.value.zip;
      this.personDetails.address.city = this.secondFormGroup.value.city;
      this.personDetails.address.country = this.secondFormGroup.value.country;
    }
    if (!this.firstFormGroup.pristine || !this.secondFormGroup.pristine) {
      this._data.savePersonDetails(this.personDetails).subscribe(d => {
        this._snackBar.open("Your details are updated successfully");
        this.firstFormGroup.pristine = true;
        this.secondFormGroup.pristine = true;
      });
    }
    this.initConfig();
  }

  completeOrder() {
    this._data.getCompleteOrderTime().subscribe(d => {
      this.goForward.emit();
      this.orderDateTime = d;
      this.setOrderDateTime.emit(this.orderDateTime);
      this.refreshCart.emit();
    });
  }

  private initConfig() {

    this.totalPay = this.totalPay.replace(',', '.');
    this.transactionItems = this.createTransactionItems(this.productCostList);
    this.payPalConfig = {
      currency: 'EUR',
      clientId: this.clientId,
      createOrderOnClient: (data) => <ICreateOrderRequest>{
        intent: 'CAPTURE',
        purchase_units: [{
          amount: {
            currency_code: 'EUR',
            value: this.totalPay,
            breakdown: {
              item_total: {
                currency_code: 'EUR',
                value: this.totalPay,
              }
            }
          },
          items: this.transactionItems
        }]
      },
      advanced: {
        commit: 'true'
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });

      },
      onClientAuthorization: (data) => {
        console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
        this.completeOrder();
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
        // this.showCancel = true;

      },
      onError: err => {
        console.log('OnError', err);
        // this.showError = true;
      },
      onClick: (data, actions) => {
        console.log('onClick', data, actions);
        // this.resetStatus();
      }
    };
  }

  private createTransactionItems(list: ProductCostElement[]) {
    return list.map(function (element) {
      let amount = new UnitAmount();
      amount.currency_code = 'EUR';
      // amount.value = (element.price - element.price * element.discount / 100) + '';
      amount.value = element.price + '';
      let item = new TransactionItem();
      item.name = element.product.name;
      item.unit_amount = amount;
      item.quantity = element.quantity + '';
      return item;
    });
  }

}

export interface OrderDateTime {
  year: number;
  monthValue: number;
  dayOfMonth: number;
  hour: number;
  minute: number;
}

export class TransactionItem implements ITransactionItem {
  name: string;
  quantity: string;
  unit_amount: IUnitAmount;
}

export class UnitAmount implements IUnitAmount {
  currency_code: string;
  value: string;
}


