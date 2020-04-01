import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {KeycloakService} from "../service/keycloak.service";
import {DataService} from "../service/data.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Router} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-sidenav-responsive',
  templateUrl: './sidenav-responsive.component.html',
  styleUrls: ['./sidenav-responsive.component.css'],
  animations: [
    trigger('searchOpenClose', [
      state('open', style({ width: '70%'})),
      state('closed', style({width: '0px', minWidth: '0'})),
      transition('open <=> closed', animate('0.5s')),
    ]),
  ],
})
export class SidenavResponsiveComponent implements OnDestroy, OnInit {

  mobileQuery: MediaQueryList;
  private readonly _mobileQueryListener: () => void;
  cart: any;

  isOpen = false;

  constructor(private _changeDetectorRef: ChangeDetectorRef,
              private _media: MediaMatcher,
              private _auth: KeycloakService,
              private _data: DataService,
              private _router: Router,
              private _location: Location) {
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

  toggle() {
    this.isOpen = !this.isOpen;
  }

  // getDetails() {
  //   this._data.getPersonDetails().subscribe(d => {
  //     return console.log("Admin: " + JSON.stringify(d));
  //   });
  // }

  countProductsInCart() {
    // this.data.getCartProducts().subscribe(d => {
    //   return this.cart = d.length;
    // });
    this._data.getQuantitiesSumInCart().subscribe(d => {
      return this.cart = d;
    })

  }

  applySearch(event: Event) {
    const searchValue = (event.target as HTMLInputElement).value;
    console.log(searchValue.trim().toLowerCase());
    console.log(this._location.path());
    this._router.navigateByUrl("");

  }

  ngOnInit(): void {
    this.countProductsInCart();
  }
}
