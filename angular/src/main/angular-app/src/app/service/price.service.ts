import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PriceService {

  constructor() { }

  createPrice(arr: any[]): string {
    let resultArr = arr.map(function (x) {
      return x.price;
    });
    let min = Math.min(...resultArr);
    let max = Math.max(...resultArr);
    return this.convertToEuroPrice(min) + " - " + this.convertToEuroPrice(max);
  }

  convertToEuroPrice(val: number): string {
    return val?.toFixed(2).replace('.', ',');
  }
}
