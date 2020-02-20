import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  private get(path) {
    return this.http.get(this.baseUrl + path);
  }

  private post(path, body) {
    return this.http.post(this.baseUrl + path, body);
  }

  public getDetails() {
    return this.get('/person/details');
  }

  public getAllProducts(): Observable<any> {
    return this.get('/product').pipe();
  }

  public getCartProducts(): Observable<any> {
    return this.get('/product/cart').pipe();
  }

  public addProductToCart(body) {
    return this.post('/order_product_cost/add', body);
  }

  // public getDetails(path) {
  //   const url = this.baseUrl + '/person';
  //   return this.http.get(url + path);
  // }
  //
  // public getAllProducts(): Observable<any> {
  //   return this.http.get(this.baseUrl + '/product').pipe();
  // }
  //
  // public getProduct(path) {
  //   const url = this.baseUrl + '/product';
  //   return this.http.get(url + path);
  // }
  //
  // public postOrderProduct(body) {
  //   const url = this.baseUrl + '/order_product_cost/add';
  //   // const body = {productId: productId, quantity: 2};
  //   // console.log(body);
  //   return this.http.post(url, body).subscribe(d=> {console.log(d)});
  // }
}



