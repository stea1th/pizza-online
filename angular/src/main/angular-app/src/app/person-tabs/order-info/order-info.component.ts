import { Component, OnInit } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.css']
})
export class OrderInfoComponent implements OnInit {

  years = [2019, 2018, 2017];
  selected = 'sixMonth';
  treeControl= new FlatTreeControl<ExampleFlatNode>(
  node => node.level, node => node.expandable);


  constructor() { }

  ngOnInit(): void {
  }



}

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}
