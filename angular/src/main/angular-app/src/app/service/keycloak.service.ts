import {Injectable} from '@angular/core';

declare var Keycloak: any;

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  static auth: any = {};

  init(): Promise<any> {
    return new Promise((resolve, reject) => {
      const keycloakAuth = Keycloak('assets/keycloak/keycloak.json');
      keycloakAuth.init({onLoad: 'login-required'})
        .success(() => {
          KeycloakService.auth.loggedIn = true;
          KeycloakService.auth.keycloak = keycloakAuth;
          KeycloakService.auth.logoutUrl = keycloakAuth.authServerUrl
            + 'realms/Pizza-Online-Realm/protocol/openid-connect/logout?redirect_uri='
            + document.baseURI;
          // console.log(KeycloakService.auth);
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
          .success((refreshed) => {
            // console.log('refreshed ' + refreshed);
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

  logout() {
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz = null;
    window.location.href = KeycloakService.auth.logoutUrl;
  }

}
