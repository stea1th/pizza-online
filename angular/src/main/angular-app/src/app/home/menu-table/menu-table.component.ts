import {AfterContentInit, Component, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {DataService} from "../../service/data.service";
import {Creator} from "../../helper/creator";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {DomSanitizer} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatSort} from "@angular/material/sort";
import {SearchService} from "../../service/search.service";
import {Router} from "@angular/router";

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
export class MenuTableComponent implements OnInit, AfterContentInit {

  myDataSource = new MatTableDataSource<ProductElement>();
  testData = `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;

  columnsToDisplay = ['name', 'description', 'price', 'discount'];
  expandedElement: ProductElement | null;

  searchValue = '';

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private _data: DataService,
              private _sanitizer: DomSanitizer,
              private _snackBar: MatSnackBar,
              private _search: SearchService,
              ) {
  }

  ngOnInit() {
    // console.log(this.searchValue);
    this.fillTable();
    // this.searchTable();
  }

  ngAfterContentInit() {
    this._search.find.subscribe(data => {
      this.myDataSource.filter = data;
      // this.searchValue = data;
      console.log(this.myDataSource.filter);
    });
  }

  searchTable() {
    // this._search.find.subscribe(data => {
    //   console.log(data);

    //
    //   console.log(this.myDataSource.filter);
    // });
  }

  fillTable() {
    this._data.getAllProducts().subscribe(data => {
      // this.myDataSource = new MatTableDataSource<ProductElement>(data);
      this.myDataSource.data = data as ProductElement[];
      this.myDataSource.paginator = this.paginator;
      for (let i = 0; i < this.myDataSource.filteredData.length; i++) {
        this.myDataSource.filteredData[i].picture = 'data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(data[i].picture) as any)
          .changingThisBreaksApplicationSecurity;
        this.myDataSource.filteredData[i].price = this.createPrice(data[i].productCostList);
        this.myDataSource.filteredData[i].discount = data[i].productCostList.filter(x => x.discount > 0).length > 0 ? '%' : '';
        this.myDataSource.sort = this.sort;
        this.myDataSource.paginator = this.paginator;
      }
    });
  }

  closeRow() {
    this.expandedElement = null;
  }

  createPrice(arr: any[]): string {
    let resultArr = arr.map(function (x) {
      return x.price;
    });
    let min = Math.min(...resultArr);
    let max = Math.max(...resultArr);
    return Creator.createPrice(min) + " - " + Creator.createPrice(max);
  }

  openSnackBar(message: string) {
    this._snackBar.open(message);
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
