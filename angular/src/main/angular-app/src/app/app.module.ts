import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatTableModule} from "@angular/material/table";
import { MenuTableComponent } from './home/menu-table/menu-table.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {NgKeycloakModule} from "ng-keycloak";
import {KeycloakService} from "./service/keycloak.service";
import {TokenInterceptor} from "./service/token-interceptor";
import { SidenavResponsiveComponent } from './sidenav-responsive/sidenav-responsive.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatBadgeModule} from "@angular/material/badge";
import {ReactiveFormsModule} from "@angular/forms";
import { AddToCartFormComponent } from './home/menu-table/add-to-cart-form/add-to-cart-form.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import { ShopCartComponent } from './cart-stepper/shop-cart/shop-cart.component';
import { ProductCardComponent } from './cart-stepper/shop-cart/product-card/product-card.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import { FooterTotalComponent } from './cart-stepper/shop-cart/footer-total/footer-total.component';
import {MatStepperModule} from "@angular/material/stepper";
import { CartStepperComponent } from './cart-stepper/cart-stepper.component';
import { HeaderCardComponent } from './cart-stepper/shop-cart/header-card/header-card.component';
import { PersonTabsComponent } from './person-tabs/person-tabs.component';
import {MatTabsModule} from "@angular/material/tabs";
import { PersonInfoComponent } from './person-tabs/person-info/person-info.component';

export function kcFactory(keycloakService: KeycloakService) {
  return () => keycloakService.init();
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    MenuTableComponent,
    SidenavResponsiveComponent,
    AddToCartFormComponent,
    ShopCartComponent,
    ProductCardComponent,
    FooterTotalComponent,
    CartStepperComponent,
    HeaderCardComponent,
    PersonTabsComponent,
    PersonInfoComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatButtonModule,
        MatProgressBarModule,
        MatTableModule,
        FontAwesomeModule,
        NgKeycloakModule,
        MatSidenavModule,
        MatListModule,
        FlexLayoutModule,
        MatBadgeModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatOptionModule,
        MatSelectModule,
        MatInputModule,
        MatCardModule,
        FontAwesomeModule,
        MatPaginatorModule,
        MatStepperModule,
        MatTabsModule,

    ],
  providers: [
    KeycloakService,
    {
      provide: APP_INITIALIZER,
      useFactory: kcFactory,
      deps: [KeycloakService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

