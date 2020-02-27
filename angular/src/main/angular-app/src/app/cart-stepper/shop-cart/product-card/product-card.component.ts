import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductCostElement} from "../shop-cart.component";
import {FormControl, Validators} from "@angular/forms";
import {faEuroSign} from "@fortawesome/free-solid-svg-icons";
import {Creator} from "../../../helper/creator";
import {DataService} from "../../../service/data.service";
import {SidenavResponsiveComponent} from "../../../sidenav-responsive/sidenav-responsive.component";
import {DomSanitizer} from "@angular/platform-browser";


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input("product-element") productCostElement: ProductCostElement;

  @Output() refreshElements = new EventEmitter();

  formNormalPrice: string;
  quantity: string;
  quantitySelect;
  faEuro = faEuroSign;
  image: string;

  constructor(private _data: DataService, private _sideNav: SidenavResponsiveComponent, private _sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.quantity = '' + this.productCostElement.quantity;
    this.formNormalPrice = Creator.createPrice(this.productCostElement.price);
    this.quantitySelect = new FormControl(this.quantity, Validators.required);
    this.quantitySelect.markAsPristine();
    this.image = 'data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(this.productCostElement.product.picture) as any).changingThisBreaksApplicationSecurity;

  }

  createRange() {
    return Array(this.productCostElement.quantity + 5);
  }

  onChange() {
    if(this.quantitySelect.pristine == false) {
      const body = {productCostId: this.productCostElement.id, quantity: this.quantitySelect.value};
      this._data.updateProductQuantityInCart(body).subscribe((d) => {
        this._sideNav.cart = d;
        this.refreshElements.emit();
      });
    }
  }

  removeFromCart() {
    this._data.deleteProductFromCart(this.productCostElement.id).subscribe((d) => {
      this._sideNav.cart = d;
      this.refreshElements.emit();
    });
  }
}
