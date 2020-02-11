import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  baseUrl = 'http://localhost:8081/api';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  public getAllProducts(): Observable<any> {
    return this.http.get(this.baseUrl + '/product').pipe();
  }

}
