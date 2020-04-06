import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {PersonDetails} from "../person-tabs/person-tabs.component";
import {OrderDateTime} from "../cart-stepper/person-details/person-details.component";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = 'http://localhost:8081/api';

  constructor(private _http: HttpClient) {
  }

  private get(path) {
    return this._http.get(this.baseUrl + path);
  }

  private post(path, body) {
    return this._http.post(this.baseUrl + path, body);
  }

  private delete(path, options) {

    return this._http.delete(this.baseUrl + path, options);
  }

  public getPersonDetails(): Observable<any> {
    return this.get('/person/details').pipe();
  }

  public savePersonDetails(body) {
    return this.post('/person/save', body);
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
    return this.get('/product-cost/cart').pipe();
  }

  public updateProductQuantityInCart(body) {
    return this.post('/order_product_cost/update', body);
  }

  public deleteProductFromCart(id) {
    const params = new HttpParams().set('productCostId', id);
    let options = {params: params};
    return this.delete('/order_product_cost/delete', options);
  }

  public getCompleteOrderTime(): Observable<any> {
    return this.get('/order/complete').pipe();
  }

  public getTimeInterval(): Observable<any> {
    return this.get('/order/interval').pipe();
  }
}



