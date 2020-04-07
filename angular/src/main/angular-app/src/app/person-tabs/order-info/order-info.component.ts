import { Component, OnInit } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {DataService} from "../../service/data.service";

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.css']
})
export class OrderInfoComponent implements OnInit {

  years: TimeInterval[];
  selected: string;
  // treeControl= new FlatTreeControl<ExampleFlatNode>(
  // node => node.level, node => node.expandable);


  constructor(private _data: DataService) { }

  ngOnInit(): void {
    this._data.getTimeInterval().subscribe(data => {
      this.years = data;
      this.selected = this.years[0].name;
    });
  }



}

interface TimeInterval {
  name: string;
  description: string;
}
