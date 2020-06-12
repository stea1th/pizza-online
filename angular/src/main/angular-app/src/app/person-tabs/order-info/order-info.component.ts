import {Component, OnInit} from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {DataService} from "../../service/data.service";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.css']
})
export class OrderInfoComponent implements OnInit {

  years: TimeInterval[];
  selected: string;
  timeIntervalSelect = new FormControl('');
  completedOrders = [];


  constructor(private _data: DataService) {
  }

  ngOnInit(): void {
    this._data.getTimeInterval().subscribe(data => {
      this.years = data;
      this.selected = this.years[0].name;
      this.timeIntervalSelect.setValue(this.years[0].name);
    });
  }


  onChange() {
    const params = '/' + this.timeIntervalSelect.value;
    this.completedOrders = [];
    this._data.getCompletedOrders(params).subscribe(data => {
      data.forEach(order => {
        const completedOrder = new CompletedOrder();
        completedOrder.id = order.orderDto.id;
        // const completed = order.orderDto.completed;
        completedOrder.completed = new Date(order.orderDto.completed);
        completedOrder.products = order.cartElementList;
        this.completedOrders.push(completedOrder);
      });
      console.log(this.completedOrders);
    })
  }
}

export interface TimeInterval {
  name: string;
  description: string;
}

export class CompletedOrder {
  id: number;
  completed: Date;
  products: [];
}

