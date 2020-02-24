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

  public getQuantitiesSumInCart() {
    return this.get('/order_product_cost/sum');
  }

  public addProductToCart(body) {
    return this.post('/order_product_cost/add', body);
  }

  public getCartProductCosts(): Observable<any> {
    return  this.get('/product-cost/cart').pipe();
  }
}



