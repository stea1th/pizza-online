import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ProductElement} from "../../../home/menu-table/menu-table.component";
import {PriceService} from "../../../service/price.service";
import {MatSelectionList} from "@angular/material/list";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  @Input() productElement: ProductElement;
  @ViewChild('selection') selection: MatSelectionList;

  testData = `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;

  constructor(private _price: PriceService,
  ) {
  }

  ngOnInit(): void {
  }

  changeSelection() {

  }

  toggleAll() {


    this.selection.selectAll();
  }

}
