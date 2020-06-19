import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ICreateOrderRequest, IPayPalConfig, ITransactionItem, IUnitAmount} from "ngx-paypal";
import {SpinnerService} from "../../../service/spinner.service";

@Component({
  selector: 'app-paypal-payment',
  templateUrl: './paypal-payment.component.html',
  styleUrls: ['./paypal-payment.component.css']
})
export class PaypalPaymentComponent implements OnInit {

  clientId = 'AYKcsiSUWdhRnlVsPq6-aid-OnWm1UqL_dbFw3lsqei_Cz_VLUX6hGeXMrne5I4YsoBQR6ceeVIjVNl-';

  payPalConfig?: IPayPalConfig;

  @Output() completeOrder = new EventEmitter();

  // transactionItems: ITransactionItem[];

  constructor(private _spinner: SpinnerService) { }

  ngOnInit(): void {
  }

  public initConfig(totalPay: string, transactionItems: TransactionItem[]) {
    totalPay = totalPay.replace(',', '.');
    // this.transactionItems = this.createTransactionItems(this.productCostList);
    this.payPalConfig = {
      currency: 'EUR',
      clientId: this.clientId,
      createOrderOnClient: (data) => <ICreateOrderRequest>{
        intent: 'CAPTURE',
        purchase_units: [{
          amount: {
            currency_code: 'EUR',
            value: totalPay,
            breakdown: {
              item_total: {
                currency_code: 'EUR',
                value: totalPay,
              }
            }
          },
          items: transactionItems
        }]
      },
      advanced: {
        commit: 'true'
      },
      style: {
        // label: 'paypal',
        // layout: 'vertical',
        label: "pay",
        color: "gold",
        shape: "pill",
        size: "responsive",
        layout: "horizontal",
      },
      onApprove: (data, actions) => {
        this._spinner.showSpinner();
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });

      },
      onClientAuthorization: (data) => {
        console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
        this.completeOrder.emit();
        this._spinner.stopSpinner();
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
