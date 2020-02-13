import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  public getAuth(path) {
    const url = `${this.baseUrl}/person`;
    return this.http.get(url + path);
  }

  public getAllProducts(): Observable<any> {
    return this.http.get(this.baseUrl + '/product').pipe();
  }

}
