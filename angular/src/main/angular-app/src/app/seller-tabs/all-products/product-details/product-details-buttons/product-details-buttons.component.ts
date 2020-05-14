import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatSelectionList} from "@angular/material/list";
import {MatTextColumn} from "@angular/material/table";

@Component({
  selector: 'app-product-details-buttons',
  templateUrl: './product-details-buttons.component.html',
  styleUrls: ['./product-details-buttons.component.css']
})
export class ProductDetailsButtonsComponent implements OnInit {

  @Input() selection: MatSelectionList;
  // @ViewChild('myTextArea') textArea: MatTextColumn<any>

  constructor() { }

  ngOnInit(): void {
  }

  hasValue() {
    return this.selection.selectedOptions.selected.length != 0;
  }

  oneSelected() {
    return this.selection.selectedOptions.selected.length != 1;
  }



}
