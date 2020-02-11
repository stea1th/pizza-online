import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  public getAuth(path) {
    const url = `${this.baseUrl}/authenticate`;
    return this.http.get(url + path);
  }
}
