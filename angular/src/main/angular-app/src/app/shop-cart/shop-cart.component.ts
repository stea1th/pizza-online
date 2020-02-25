import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Creator} from "../helper/creator";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  productCostList: ProductCostElement[];
  totalPay: string;
  totalQuantity: number;

  constructor(private data: DataService) {
  }

  ngOnInit(): void {
    this.getProductsInCart();
  }

  getProductsInCart() {
    this.data.getCartProductCosts().subscribe(data => {
      this.productCostList = data;
      this.createTotal(this.productCostList);
      return this.productCostList;
    });
  }

  createTotal(array: ProductCostElement[]) {
    let totalPrice = 0;
    this.totalQuantity = 0;
    for (let i = 0; i < array.length; i++) {
      const price = array[i].price;
      const quantity = array[i].quantity;
      totalPrice += (price - price * array[i].discount / 100) * quantity;
      this.totalQuantity += quantity;
    }
    this.totalPay = Creator.createPrice(totalPrice);
  }



  createQuantitiesSum(array: ProductCostElement[]) {
    let total = 0;
    for (let i = 0; i < array.length; i++) {
      total += array[i].quantity;
    }
    return total;
  }
}

export class ProductCostElement {
  id: number;
  property: string;
  price: number;
  discount: number;
  quantity: number;
  product: any;
}
