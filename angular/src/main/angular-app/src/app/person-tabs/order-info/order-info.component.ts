import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.css']
})
export class OrderInfoComponent implements OnInit {

  years = [2019, 2018, 2017];
  selected = 'sixMonth';


  constructor() { }

  ngOnInit(): void {
  }



}
