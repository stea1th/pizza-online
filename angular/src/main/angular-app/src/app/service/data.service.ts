import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  // baseUrl = 'http://localhost:8081/api';
  // baseUrl='http://localhost:8662';
  baseUrl='http://localhost:8663';

  constructor(private _http: HttpClient) {
  }

  private get(path) {
    // return this._http.get(this.baseUrl + path);
    return this.getWithParams(path, '');
  }

  private getWithParams(path, params) {
    return this._http.get(this.baseUrl + path + params);
  }

  private post(path, body) {
    return this._http.post(this.baseUrl + path, body);
  }

  private delete(path, id) {

    // return this._http.delete(this.baseUrl + path, options);
    return this._http.delete(this.baseUrl + path + id);
  }

  public getPersonDetails(): Observable<any> {
    // return this.get('/person/details').pipe();
    return this.get('/person/api/details').pipe();
  }

  public savePersonDetails(body) {
    // return this.post('/person/save', body);
    return this.post('/person/api/save', body);
  }

  public getAllProducts(params): Observable<any> {
    // return this.get('/product').pipe();
    return this.getWithParams('/product/api/products', params).pipe();
  }

  // public getAllProductsWithoutFrozen(withFrozen : boolean): Observable<any> {
  //   // return this.get('/product/no-frozen').pipe();
  //   return this.get('/product/api/product/no-frozen').pipe();
  // }

  // public getCartProducts(): Observable<any> {
  //   return this.get('/product/cart').pipe();
  // }

  public getQuantitiesSumInCart() {
    // return this.get('/order_product_cost/sum');
    return this.get('/order-product/api/sum');
  }

  public addProductToCart(body) {
    // return this.post('/order_product_cost/add', body);
    return this.post('/order-product/api/add', body);
  }

  public getCartProductCosts(): Observable<any> {
    // return this.get('/product-cost/cart').pipe();
    return this.get('/order-product/api/cart').pipe();
  }

  public updateProductQuantityInCart(body) {
    // return this.post('/order_product_cost/update', body);
    return this.post('/order-product/api/update', body);
  }

  public deleteProductFromCart(id) {
    // const params = new HttpParams().set('productCostId', id);
    // let options = {params: params};
    // return this.delete('/order_product_cost/delete', options);
    return this.delete('/order-product/api/delete/', id);
  }

  public getCompleteOrderTime(): Observable<any> {
    // return this.get('/order/complete').pipe();
    return this.get('/order/api/complete').pipe();
  }

  public getTimeInterval(): Observable<any> {
    // return this.get('/order/interval').pipe();
    return this.get('/order/api/interval').pipe();

  }

  public getCompletedOrders(params): Observable<any> {
    // return this.getWithParams('/order/all/completed', params).pipe();
    return this.getWithParams('/completed-order/api/completed', params);
  }
}



