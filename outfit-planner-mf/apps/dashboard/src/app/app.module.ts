import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NxWelcomeComponent} from './nx-welcome.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {provideAnimations} from "@angular/platform-browser/animations";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {ButtonModule} from "primeng/button";
import {SidebarModule} from "primeng/sidebar";
import {KeycloakAngularModule, KeycloakBearerInterceptor, KeycloakService} from "keycloak-angular";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuard} from "@outfit-planner-mf/shared/auth";
import {MenuModule} from "primeng/menu";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {NavigationBarComponent} from "./navigation-bar/navigation-bar.component";
import {NavigationComponentsModule} from "@outfit-planner-mf/shared/components";
import {ProgressSpinnerModule} from "primeng/progressspinner";
@NgModule({
  declarations: [AppComponent, NxWelcomeComponent, NavigationBarComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {initialNavigation: 'enabledBlocking'}),
    MatIconModule,
    MatButtonModule,
    ButtonModule,
    SidebarModule,
    KeycloakAngularModule,
    MenuModule,
    NgbTooltip,
    NavigationComponentsModule,
    ProgressSpinnerModule
  ],
  providers: [
    provideAnimations(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true
    },
    {
      provide: KEYCLOAK_GUARD_CONFIG,
      useValue: {
        clientId: 'outfit-service'
      },
    },
    KeycloakService,
    KeycloakGuard,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
