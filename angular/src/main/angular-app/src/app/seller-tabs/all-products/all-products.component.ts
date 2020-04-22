import { Component, OnInit } from '@angular/core';
import {DataService} from "../../service/data.service";
import {MatTableDataSource} from "@angular/material/table";
import {ProductElement} from "../../home/menu-table/menu-table.component";
import {DomSanitizer} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SpinnerService} from "../../service/spinner.service";

@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.css']
})
export class AllProductsComponent implements OnInit {

  displayedColumns: string[] = ['select', 'position', 'name', 'weight', 'symbol'];

  myDataSource = new MatTableDataSource<ProductElement>();
  testData = `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;

  constructor(private _data: DataService,
              private _sanitizer: DomSanitizer,
              private _snackBar: MatSnackBar,
              private _spinner: SpinnerService,) { }

  ngOnInit(): void {

  }

  fillTable() {
    this._spinner.showSpinner();
    this._data.getAllProducts().subscribe(data=> {
      this.myDataSource.data = data as ProductElement[];



      this._spinner.stopSpinner();
    });
  }

}
