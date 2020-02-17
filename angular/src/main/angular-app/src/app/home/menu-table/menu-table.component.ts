import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {DataService} from "../../service/data.service";

@Component({
  selector: 'app-menu-table',
  templateUrl: './menu-table.component.html',
  styleUrls: ['./menu-table.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class MenuTableComponent implements OnInit {

  myDataArray: ProductElement[];
  testData = `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;

  columnsToDisplay = ['name', 'description', 'price', 'discount'];
  expandedElement: ProductElement | null;

  constructor(private dataService : DataService) {
  }

  ngOnInit() {
    this.dataService.getAllProducts().subscribe(data => {
      this.myDataArray = data;
      this.myDataArray.forEach(i => i.price = i.price.toFixed(2));
    });

  }

  test(num: number) {
    console.log(num);
    this.dataService.postOrderProduct(num);
  }
}

export interface ProductElement {
  id: number;
  name: string;
  price: any;
  discount: number;
  description: string;
  picture: string;
}
