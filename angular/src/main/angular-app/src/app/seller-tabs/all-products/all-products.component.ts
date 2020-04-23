import {Component, OnInit, ViewChild} from '@angular/core';
import {DataService} from "../../service/data.service";
import {MatTableDataSource} from "@angular/material/table";
import {ProductElement} from "../../home/menu-table/menu-table.component";
import {DomSanitizer} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SpinnerService} from "../../service/spinner.service";
import {SelectionModel} from "@angular/cdk/collections";
import {PriceService} from "../../service/price.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ProductDetailsComponent} from "./product-details/product-details.component";

@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class AllProductsComponent implements OnInit {

  displayedColumns: string[] = ['select', 'picture', 'name', 'price', 'discount'];

  myDataSource = new MatTableDataSource<ProductElement>();
  selection = new SelectionModel<ProductElement>(true, []);

  @ViewChild('productDetails') productDetails: ProductDetailsComponent;

  expandedElement: ProductElement | null;

  constructor(private _data: DataService,
              private _sanitizer: DomSanitizer,
              private _snackBar: MatSnackBar,
              private _spinner: SpinnerService,
              private _price: PriceService) { }

  ngOnInit(): void {
    this.fillTable();

  }

  fillTable() {
    this._spinner.showSpinner();
    this._data.getAllProducts().subscribe(data=> {
      this.myDataSource.data = data as ProductElement[];
      for (let i = 0; i < this.myDataSource.filteredData.length; i++) {
        this.myDataSource.filteredData[i].picture = 'data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(data[i].picture) as any)
          .changingThisBreaksApplicationSecurity;
        this.myDataSource.filteredData[i].price = this._price.createPrice(data[i].productCostList);
        this.myDataSource.filteredData[i].discount = data[i].productCostList.filter(x => x.discount > 0).length > 0 ? '%' : '';
      }
      this._spinner.stopSpinner();
    });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.myDataSource.data.length;
    return numSelected === numRows;
  }

  toggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.myDataSource.data.forEach(row => this.selection.select(row));
  }

  checkIfSelected(row?: ProductElement) {
    const isSelected = this.selection.isSelected(row);
    if (isSelected) this.productDetails.toggleAll();
    return isSelected;
  }



  checkboxLabel(row?: ProductElement): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id}`;
  }

}
