import {Injectable} from '@angular/core';

declare var Keycloak: any;

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  // private keycloakAuth: any;
  //
  // constructor() {
  // }
  //
  // init(): Promise<any> {
  //   return new Promise((resolve, reject) => {
  //     const config = {
  //       'url': 'http://localhost:4200',
  //       'realm': 'Pizza-Online Realm',
  //       'clientId': 'pizza-online-frontend'
  //     };
  //     this.keycloakAuth = new Keycloak(config);
  //     this.keycloakAuth.init({onLoad: 'login-required'})
  //       .success(() => {
  //         resolve();
  //       })
  //       .error(() => {
  //         reject();
  //       });
  //   });
  // }
  //
  // getToken(): string {
  //   return this.keycloakAuth.token;
  // }

  // private keycloakAuth: any;
  static auth: any = {};

  init(): Promise<any> {
    return new Promise((resolve, reject) => {
      const keycloakAuth = Keycloak('assets/keycloak/keycloak.json');
      keycloakAuth.init({onLoad: 'login-required'})
        .done(() => {
          KeycloakService.auth.loggedIn = true;
          KeycloakService.auth.keycloak = keycloakAuth;
          KeycloakService.auth.logoutUrl = keycloakAuth.authServerUrl
            + '/realms/Pizza-Online-Realm/protocol/openid-connect/logout?redirect_uri='
            + document.baseURI;
          console.log(KeycloakService.auth);
          resolve();
        })
        .error(() => {
          reject();
        });
    });
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.keycloak.token) {
        KeycloakService.auth.keycloak
          .updateToken(60)
          .done((refreshed) => {
            console.log('refreshed ' + refreshed);
            resolve(<string>KeycloakService.auth.keycloak.token);
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      } else {
        reject('Not logged in');
      }
    });
  }

  getParsedToken() {
    return KeycloakService.auth.keycloak.tokenParsed;
  }

}
