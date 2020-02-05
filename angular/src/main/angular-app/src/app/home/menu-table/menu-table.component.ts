import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MenuService} from "../../service/menu.service";

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

  columnsToDisplay = ['name', 'description', 'price', 'discount'];
  expandedElement: ProductElement | null;

  constructor(private menuService: MenuService) {
  }

  ngOnInit() {
    this.menuService.getAllProducts().subscribe(data => {
      this.myDataArray = data;
      this.myDataArray.forEach(i => i.price = i.price.toFixed(2));
    });

  }

  test(num: number) {
    console.log(num);
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
