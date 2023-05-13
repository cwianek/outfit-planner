import {Inject, Injectable, InjectionToken} from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate,
  Router,
  RouterStateSnapshot, UrlTree
} from '@angular/router';
import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';
import {from, map, Observable, of, shareReplay, switchMap, tap} from "rxjs";

export const KEYCLOAK_GUARD_CONFIG = new InjectionToken<string>('KeycloakGuardConfig');

export interface KeycloakGuardConfig {
  clientId: string
}

@Injectable({
  providedIn: 'root'
})
export class KeycloakGuard implements CanActivate {

  keycloakInitialization: Observable<boolean> | null = null;

  constructor(
    private keycloak: KeycloakService,
    @Inject(KEYCLOAK_GUARD_CONFIG)
    private keycloakConfig: KeycloakGuardConfig
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.isLoggedIn().pipe(
      switchMap((loggedIn: boolean) => {
        if (!loggedIn) {
          return this.keycloakLogin(state);
        }
        return of(loggedIn)
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
