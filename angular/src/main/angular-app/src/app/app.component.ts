import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "./service/keycloak.service";
import {DataService} from "./service/data.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'angular-app';
  cart: any;



  constructor(private auth: KeycloakService, private data: DataService) {
  }

  logout() {
    this.auth.logout();
  }

  getDetails() {
    this.data.getAuth('/details').subscribe(d => {
      return console.log("Admin: " + JSON.stringify(d));
    });
  }

  getProductsInCart() {
    this.data.getProduct('/cart').subscribe(d => {
       // console.log("Cart: " + JSON.stringify(d));
       console.log("Cart: " + d);
      return this.cart = d;
    });

  }

  ngOnInit(): void {
    this.getProductsInCart();
  }

  deleteProductsFromCart() {

  }
}
