import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {DataService} from "../../service/data.service";
import {Creator} from "../../helper/creator";

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

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.dataService.getAllProducts().subscribe(data => {
      this.myDataArray = data;
      for (let i = 0; i < this.myDataArray.length ; i++) {
        this.myDataArray[i].price = this.createPrice(data[i].productCostList);
        this.myDataArray[i].discount = data[i].productCostList.filter(x=> x.discount > 0).length > 0 ? '%' : '';
      }
    });
  }

  closeRow() {
    this.expandedElement = null;
  }

  createPrice(arr: any[]): string {
    let resultArr = arr.map(function(x) {
      return x.price;
    });
    let min = Math.min(...resultArr);
    let max = Math.max(...resultArr);
    return Creator.createPrice(min) + " - " + Creator.createPrice(max);
  }
}

export class ProductElement {
  id: number;
  name: string;
  productCostList: [];
  price: any;
  description: string;
  picture: string;
  discount: string;
}
