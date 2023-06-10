import {Inject, Injectable} from '@angular/core';
import {BehaviorSubject, from, Observable} from "rxjs";
import {Router, RouterStateSnapshot} from "@angular/router";
import {KeycloakService} from "keycloak-angular";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuardConfig} from "./keycloak.guard";

@Injectable(
  {
    providedIn: 'root'
  }
)
export class UserService {
  keycloakInstance: KeycloakService = new KeycloakService();

  constructor(
    private router: Router,
    @Inject(KEYCLOAK_GUARD_CONFIG)
    private keycloakConfig: KeycloakGuardConfig) {
  }

  private isUserLoggedIn = new BehaviorSubject(false);
  isUserLoggedIn$ = this.isUserLoggedIn.asObservable();

  setLoggedIn(value: boolean) {
    this.isUserLoggedIn.next(value);
  }

  logOut() {
    this.keycloakInstance.logout(window.location.origin + '/');
  }

  logIn() {
    this.keycloakLogin(this.router.routerState.snapshot).subscribe()
  }

  setKeycloakInstance(service: KeycloakService) {
    this.keycloakInstance = service;
  }

  private keycloakLogin = (state: RouterStateSnapshot): Observable<void> => {
    return from(this.keycloakInstance.login({
      redirectUri: window.location.origin + state.url
    }));
  }
}
