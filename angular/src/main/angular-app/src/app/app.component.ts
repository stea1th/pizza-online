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

  ngOnInit(): void {
  }
}
