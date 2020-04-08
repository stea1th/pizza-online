import {Component, Input, OnInit} from '@angular/core';
import {CompletedOrder} from "../order-info.component";

@Component({
  selector: 'app-order-panel',
  templateUrl: './order-panel.component.html',
  styleUrls: ['./order-panel.component.css']
})
export class OrderPanelComponent implements OnInit {

  @Input() completedOrder: CompletedOrder;
  itemQuantity: number;

  constructor() { }

  ngOnInit(): void {
    this.countItems();

  }

  countItems() {
    this.itemQuantity = 0;
    this.completedOrder.products.forEach(prod => {
      // @ts-ignore
      this.itemQuantity += prod.quantity;

    });
  }

}
