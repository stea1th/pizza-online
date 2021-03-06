import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {DataService} from "../../service/data.service";
import {MatTableDataSource} from "@angular/material/table";

import {DomSanitizer} from "@angular/platform-browser";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SpinnerService} from "../../service/spinner.service";
import {SelectionModel} from "@angular/cdk/collections";
import {PriceService} from "../../service/price.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ProductDetailsComponent} from "./product-details/product-details.component";
import {ProductElement} from "../../model/product-element.model";
import {TableDataSourceService} from "../../service/table-data-source.service";

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

  @ViewChildren('productDetails') productDetails: QueryList<ProductDetailsComponent>;

  expandedElement: ProductElement | null;

  constructor(private _data: DataService,
              private _snackBar: MatSnackBar,
              private _spinner: SpinnerService,
              private _tableService: TableDataSourceService) {
  }

  ngOnInit(): void {
    this.fillTable();
  }

  fillTable() {
    this._spinner.showSpinner();
    const params = '/' + true;
    this._data.getAllProducts(params).subscribe(data => {
      this.myDataSource = this._tableService.fillTableWithData(data as ProductElement[]);
      this._spinner.stopSpinner();
    });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.myDataSource.data.length;
    return numSelected === numRows;
  }

  toggleAll() {
    if(this.isAllSelected()) {
      this.selection.clear();
      this.myDataSource.data.forEach(row=> this.toggleAllChildrenCheckboxes(row));
    } else {
      this.myDataSource.data.forEach(row => {
        this.selection.select(row);
        this.toggleAllChildrenCheckboxes(row);
      });
    }
  }

  toggle(row: ProductElement) {
    this.selection.toggle(row);
    this.toggleAllChildrenCheckboxes(row);
  }

  toggleAllChildrenCheckboxes(row?: ProductElement) {
    const child = this.getProductDetailsComponent(row);
    this.selection.selected.forEach(el => console.log(el));
    child.toggleAll(this.checkIfSelected(row));
  }

  private getProductDetailsComponent(row: ProductElement): ProductDetailsComponent {
    return this.getProductDetailsComponentById(row.id);
  }

  private getProductDetailsComponentById(id: number): ProductDetailsComponent {
    return this.productDetails.find(el => el.productElement.id == id);
  }

  toggleCheckBox(event: any) {
    const element = this.getProductDetailsComponentById(event.id).productElement;
    event.isChecked ?
      this.selection.select(element) : this.selection.deselect(element);
  }

  checkIfSelected(row?: ProductElement) {
    return this.selection.isSelected(row);
  }

  checkboxLabel(row?: ProductElement): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id}`;
  }

}
