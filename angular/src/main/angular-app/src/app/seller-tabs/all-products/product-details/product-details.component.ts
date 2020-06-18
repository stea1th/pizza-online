import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';

import {PriceService} from "../../../service/price.service";
import {MatSelectionList} from "@angular/material/list";
import {ProductElement} from "../../../model/product-element.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  @Input() productElement: ProductElement;
  @ViewChild('selection') selection: MatSelectionList;
  @Output() checkParentCheckbox = new EventEmitter<any>();

  editDescription = false;


  testData = `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;

  descriptionFormGroup = new FormGroup( {
    descriptionForm: new FormControl(this.testData, Validators.required)
  });

  constructor(private _price: PriceService,) {
  }

  ngOnInit(): void {
  }

  toggleAll(isParentSelected: boolean) {
    isParentSelected ? this.selection.selectAll() : this.selection.selectedOptions.clear();
  }

  onChange() {
    const isChecked = this.selection.selectedOptions.selected.length != 0;
    this.checkParentCheckbox.emit({isChecked: isChecked, id: this.productElement.id});
  }

  onSubmit() {
    this.testData = this.descriptionFormGroup.value.descriptionForm;
    this.editDescription = false;
  }

  formatPrice(price: number) {
    return this._price.convertToPriceWithComma(price);
  }
}
