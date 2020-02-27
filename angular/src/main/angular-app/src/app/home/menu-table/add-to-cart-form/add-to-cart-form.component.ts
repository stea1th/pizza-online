import {Component, EventEmitter, Host, Inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DataService} from "../../../service/data.service";
import {SidenavResponsiveComponent} from "../../../sidenav-responsive/sidenav-responsive.component";
import {faEuroSign} from "@fortawesome/free-solid-svg-icons";
import {Creator} from "../../../helper/creator";
import {MatSnackBar} from "@angular/material/snack-bar";


@Component({
  selector: 'app-add-to-cart-form',
  templateUrl: './add-to-cart-form.component.html',
  styleUrls: ['./add-to-cart-form.component.css']
})
export class AddToCartFormComponent implements OnInit {

  @Input('product-cost-list') productCostList: any[];

  @Output() closeRow = new EventEmitter();

  isSubmitButtonHidden = true;
  isNormalPriceHidden = true;
  quantity = 1;

  faEuro = faEuroSign;

  formDiscountPrice: string;
  formNormalPrice: string;
  discount: number;


  addToCartForm = new FormGroup({
    productCost: new FormControl('', Validators.required),
    quantity: new FormControl('', Validators.required),
  });

  constructor(private _data: DataService, private _sideNav: SidenavResponsiveComponent) {
  }


  createRange(n: number) {
    return Array(n);
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const body = {productCostId: this.addToCartForm.value.productCost.id, quantity: this.addToCartForm.value.quantity};
    this._data.addProductToCart(body).subscribe((d) => {
      // this.closeRow.emit();

      this.resetForm();
      this._sideNav.cart = d;
    });
  }

  onChange(val: any) {
    this.discount = val?.discount;
    if(this.discount != undefined) {
      if(this.discount == 0) {
        this.formDiscountPrice = Creator.createPrice(val.price);
        this.isNormalPriceHidden = true;
      } else {
        const price = val?.price;
        this.formDiscountPrice = Creator.createPrice(price - price * this.discount / 100);
        this.formNormalPrice = Creator.createPrice(price);
        this.isNormalPriceHidden = false;
      }
    } else {
      this.formDiscountPrice = undefined;
      this.isNormalPriceHidden = true;
    }
    this.isSubmitButtonHidden = false;
  }

  private resetForm() {
    this.addToCartForm.reset({
      quantity: 1
    });
    Object.keys(this.addToCartForm.controls).forEach(key => {
      this.addToCartForm.get(key).setErrors(null);
    });
    this.isSubmitButtonHidden = true;
  }


}
