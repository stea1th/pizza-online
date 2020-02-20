import {Component, EventEmitter, Host, Inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DataService} from "../../../service/data.service";
import {SidenavResponsiveComponent} from "../../../sidenav-responsive/sidenav-responsive.component";


@Component({
  selector: 'app-add-to-cart-form',
  templateUrl: './add-to-cart-form.component.html',
  styleUrls: ['./add-to-cart-form.component.css']
})
export class AddToCartFormComponent implements OnInit {

  @Input('product-cost-list') productCostList: any[];

  @Output() closeRow = new EventEmitter();

  isHidden = true;

  formPrice: string;


  addToCartForm = new FormGroup({
    productCost: new FormControl('', Validators.required),
    quantity: new FormControl('', Validators.required),
  });

  constructor(private data: DataService, private sideNav: SidenavResponsiveComponent) {
  }


  createRange(n: number) {
    return Array(n);
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const body = {productCostId: this.addToCartForm.value.productCost.id, quantity: this.addToCartForm.value.quantity};
    this.data.addProductToCart(body).subscribe((d) => {
      console.log(d);
      this.closeRow.emit();
      this.resetForm();
      this.sideNav.countProductsInCart();
    });
  }

  onChange(val: any) {
    this.formPrice = val?.price.toFixed(2);
    this.isHidden = false;
  }

  private resetForm() {
    this.addToCartForm.reset();
    Object.keys(this.addToCartForm.controls).forEach(key => {
      this.addToCartForm.get(key).setErrors(null);
    });
    this.isHidden = true;
  }
}
