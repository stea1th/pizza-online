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

  columnsToDisplay = ['name', 'description', 'price'];
  expandedElement: ProductElement | null;

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.dataService.getAllProducts().subscribe(data => {
      this.myDataArray = new Array<ProductElement>();
      for (let i = 0; i < data.length; i++) {
        let product = new ProductElement();
        product.id = data[i].id;
        product.name = data[i].name;
        product.price = this.createPrice(data[i].productCostList);
        product.productCostList = data[i].productCostList;
        product.description=data[i].description;
        product.picture = data[i].picture;
        this.myDataArray.push(product);
      }
    });
  }

  createPrice(arr: any[]): string {
    let resultArr = arr.map(function(x) {
      return x.price;
    });
    let min = Math.min(...resultArr);
    let max = Math.max(...resultArr);
    return min.toFixed(2) + " - " + max.toFixed(2);
  }

  test(num: number) {
    console.log(num);
    this.dataService.postOrderProduct(num);
  }
}

export class ProductElement {
  id: number;
  name: string;
  productCostList: [];
  price: any;
  description: string;
  picture: string;
}
