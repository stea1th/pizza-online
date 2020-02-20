import {Component, Input, OnInit} from '@angular/core';
import {ProductElement} from "../../dto/product-element";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input("product-element") productElement: ProductElement;

  constructor() { }

  ngOnInit(): void {
  }

}
