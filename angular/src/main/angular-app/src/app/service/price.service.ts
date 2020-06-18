import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PriceService {

  constructor() {
  }

  public createMinMaxPrice(arr: any[]): string {
    let resultArr = arr.map(function (x) {
      return x.price;
    });
    let min = Math.min(...resultArr);
    let max = Math.max(...resultArr);
    return this.convertToPriceWithComma(min) + " - " + this.convertToPriceWithComma(max);
  }

  public convertToPriceWithComma(val: number): string {
    return val?.toFixed(2).replace('.', ',');
  }

  public calculateTotalPrice(arr: any[]) {
    let totalPrice = 0;
    arr.forEach(el => totalPrice += this.calculatePriceWithDiscount(el.price, el.discount) * el.quantity);
    return totalPrice;
  }

  public calculateTotalQuantity(arr: any[]) {
    let totalQuantity = 0;
    arr.forEach(el => totalQuantity += el.quantity);
    return totalQuantity;
  }

  public calculatePriceWithDiscount(price: number, discount: number) {
    return (price - price * discount / 100);
  }
}
