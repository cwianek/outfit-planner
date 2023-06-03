import {Inject, Injectable} from '@angular/core';
import {BehaviorSubject, filter, from, map, Observable, shareReplay, switchMap, tap} from "rxjs";
import {Router, RouterStateSnapshot} from "@angular/router";
import {KeycloakService} from "keycloak-angular";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuardConfig} from "./keycloak.guard";

@Injectable(
  {
    providedIn: 'root'
  }
)
export class UserService {
  keycloakInitialization: Observable<boolean> | null = null;

  keycloakInstance: KeycloakService = new KeycloakService();

  constructor( private router: Router, @Inject(KEYCLOAK_GUARD_CONFIG)
  private keycloakConfig: KeycloakGuardConfig,) {
    // this.initializeKeycloak().pipe(
    //   switchMap(() => from(this.keycloak.isLoggedIn())))
    //   .subscribe(this.isUserLoggedIn);
  }

  private isUserLoggedIn = new BehaviorSubject(false);
  isUserLoggedIn$ = this.isUserLoggedIn.asObservable();

  setLoggedIn(value: boolean) {
    this.isUserLoggedIn.next(value);
  }

  // @InvalidateCache("products")
  // @InvalidateCache("outfits")
  logOut() {
    // this.initializeKeycloak().subscribe(() => {
      this.keycloakInstance.logout(window.location.origin + '/');
    // })
  }

  logIn() {
    // this.initializeKeycloak().subscribe(() => {
      this.keycloakLogin(this.router.routerState.snapshot).subscribe((e) => {
      })
    // })
  }

  doSSO = () => {
    return this.isUserLoggedIn$.pipe(tap(() => console.log("WER ARE ERE12312")), filter((logged) => logged), tap(() => console.log("WER ARE ERE")))
  }

  initializeKeycloak = (): Observable<any> => {
    if (this.keycloakInitialization != null) {
      return this.keycloakInitialization;
    }

    this.keycloakInitialization = from(this.keycloakInstance.init({
      config: {
        url: '/auth',
        realm: 'outfitplanner-realm',
        clientId: this.keycloakConfig.clientId
      },
      initOptions: {
        checkLoginIframe: true,
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html'
      },
      enableBearerInterceptor: true,
    })).pipe(shareReplay(1));

    return this.keycloakInitialization;
  }

  setKeycloakInstance(service: KeycloakService){
    this.keycloakInstance = service;
  }

  keycloakLogin = (state: RouterStateSnapshot): Observable<boolean> => {
    return from(this.keycloakInstance.login({
      redirectUri: window.location.origin + state.url
    })).pipe(map(() => true));
  }
}
