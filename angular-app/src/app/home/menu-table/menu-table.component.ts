import { Component, OnInit } from '@angular/core';
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

  columnsToDisplay = ['id', 'name', 'price', 'discount'];
  expandedElement: ProductElement | null;

  constructor(private menuService: MenuService) { }

  ngOnInit() {
    this.menuService.getAllProducts().subscribe(data => {
      console.log(data);
      this.myDataArray = data;
    });
  }

}

export interface ProductElement {
  id: number;
  name: string;
  price: number;
  discount: number;
  description: string;
  picture: string;
}
