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

  constructor(private changeDetectorRef: ChangeDetectorRef, private media: MediaMatcher, private auth: KeycloakService, private data: DataService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addEventListener('change', () => this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeEventListener('change', () => this._mobileQueryListener);
  }

  logout() {
    this.auth.logout();
  }

  getDetails() {
    this.data.getDetails().subscribe(d => {
      return console.log("Admin: " + JSON.stringify(d));
    });
  }

  countProductsInCart() {
    this.data.getCartProducts().subscribe(d => {
      return this.cart = d.length;
    });

  }

  ngOnInit(): void {
    this.countProductsInCart();
  }
}
