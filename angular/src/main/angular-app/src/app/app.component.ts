import { Component } from '@angular/core';
import {KeycloakService} from "./service/keycloak.service";
import {DataService} from "./service/data.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';


  constructor(private auth: KeycloakService, private data: DataService) {

  }

  logout() {
    this.auth.logout();
  }
}
