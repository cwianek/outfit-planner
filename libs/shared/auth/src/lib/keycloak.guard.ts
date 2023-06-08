import {Inject, Injectable, InjectionToken} from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate,
  RouterStateSnapshot
} from '@angular/router';
import {KeycloakService} from 'keycloak-angular';
import {from, map, Observable, of, shareReplay, switchMap} from "rxjs";
import {UserService} from "./user.service";

export const KEYCLOAK_GUARD_CONFIG = new InjectionToken<string>('KeycloakGuardConfig');

export interface KeycloakGuardConfig {
  clientId: string
}

@Injectable()
export class KeycloakGuard implements CanActivate {

  keycloakInitialization: Observable<boolean> | null = null;

  constructor(
    private keycloak: KeycloakService,
    @Inject(KEYCLOAK_GUARD_CONFIG)
    private keycloakConfig: KeycloakGuardConfig,
    private userService: UserService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.isLoggedIn().pipe(
      switchMap((loggedIn: boolean) => {
        this.userService.setKeycloakInstance(this.keycloak);
        if (loggedIn) {
          this.userService.setLoggedIn(loggedIn);
        }
        return of(true)
      })
    )
  }

  private isLoggedIn = (): Observable<boolean> => {
    return this.initializeKeycloak().pipe(
      switchMap(() => this.keycloak.isLoggedIn())
    )
  }

  private keycloakLogin = (state: RouterStateSnapshot): Observable<boolean> => {
    return from(this.keycloak.login({
      redirectUri: window.location.origin + state.url
    })).pipe(map(() => true));
  }

  private initializeKeycloak = (): Observable<any> => {
    if (this.keycloakInitialization != null) {
      return this.keycloakInitialization;
    }

    this.keycloakInitialization = from(this.keycloak.init({
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
}
