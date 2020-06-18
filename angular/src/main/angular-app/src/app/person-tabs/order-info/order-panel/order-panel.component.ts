import {Component, Input, OnInit} from '@angular/core';
import {CompletedOrder} from "../order-info.component";
import {PriceService} from "../../../service/price.service";

@Component({
  selector: 'app-order-panel',
  templateUrl: './order-panel.component.html',
  styleUrls: ['./order-panel.component.css']
})
export class OrderPanelComponent implements OnInit {

  @Input() completedOrder: CompletedOrder;
  // itemQuantity: number;
  totalPay: string;

  constructor(private _price : PriceService) { }

  ngOnInit(): void {
    this.countItems();

  }

  countItems() {
    return this._price.calculateTotalQuantity(this.completedOrder.products);
  }

  countTotalPrice() {
    let totalPrice = this._price.calculateTotalPrice(this.completedOrder.products);
    return this.formatPrice(totalPrice);
}



  formatPrice(price: number) {
    return this._price.convertToPriceWithComma(price);
  }

}
