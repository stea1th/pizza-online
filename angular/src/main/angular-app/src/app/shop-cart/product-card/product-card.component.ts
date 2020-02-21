import {Component, Input, OnInit} from '@angular/core';
import {ProductCostElement} from "../shop-cart.component";


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input("product-element") productCostElement: ProductCostElement;

  constructor() { }

  ngOnInit(): void {
    console.log(this.productCostElement);
  }

}
