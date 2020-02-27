import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {KeycloakService} from "../service/keycloak.service";
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-sidenav-responsive',
  templateUrl: './sidenav-responsive.component.html',
  styleUrls: ['./sidenav-responsive.component.css']
})
export class SidenavResponsiveComponent implements OnDestroy, OnInit {
  mobileQuery: MediaQueryList;
  private readonly _mobileQueryListener: () => void;
  cart: any;

  constructor(private _changeDetectorRef: ChangeDetectorRef, private _media: MediaMatcher, private _auth: KeycloakService, private _data: DataService) {
    this.mobileQuery = _media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => _changeDetectorRef.detectChanges();
    this.mobileQuery.addEventListener('change', () => this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeEventListener('change', () => this._mobileQueryListener);
  }

  logout() {
    this._auth.logout();
  }

  getDetails() {
    this._data.getPersonDetails().subscribe(d => {
      return console.log("Admin: " + JSON.stringify(d));
    });
  }

  countProductsInCart() {
    // this.data.getCartProducts().subscribe(d => {
    //   return this.cart = d.length;
    // });
    this._data.getQuantitiesSumInCart().subscribe(d => {
      return this.cart = d;
    })

  }

  ngOnInit(): void {
    this.countProductsInCart();
  }
}
