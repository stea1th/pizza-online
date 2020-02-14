import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = 'http://localhost:8081/api';
  // orderProduct = new OrderProduct();

  constructor(private http: HttpClient) { }

  public getAuth(path) {
    const url = `${this.baseUrl}/person`;
    return this.http.get(url + path);
  }

  public getAllProducts(): Observable<any> {
    return this.http.get(this.baseUrl + '/product').pipe();
  }

  public getProduct(path) {
    const url = this.baseUrl + "/product";
    return this.http.get(url + path);
  }

  public postOrderProduct(productId: number) {
    const url = this.baseUrl + "/order_product/add";
    const body = {productId: productId, quantity: 2};
    console.log(body);
    return this.http.post(url, body).subscribe(d=> {console.log(d)});
  }
}

// export class OrderProduct {
//   orderId: number;
//   productId: number;
//   quantity: number;
// }


