import {APP_INITIALIZER, NgModule} from '@angular/core';
import {Route, Router, RouterLink, RouterModule, Routes, ROUTES} from '@angular/router';

import {RemoteEntryComponent} from './entry.component';
import {NxWelcomeComponent} from './nx-welcome.component';
import {ProductsService, SharedComponentsModule} from "@outfit-planner-mf/shared/components";
import {KeycloakAngularModule, KeycloakBearerInterceptor, KeycloakService} from "keycloak-angular";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProductListContainerComponent} from "../product-list-container/product-list-container.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuard, MockInterceptor, UserService} from "@outfit-planner-mf/shared/auth";
import {remoteRoutes} from "./entry.routes";
import {AsyncPipe} from "@angular/common";


@NgModule({
  declarations: [
    RemoteEntryComponent,
    NxWelcomeComponent,
    ProductListContainerComponent,
  ],
  imports: [
    HttpClientModule,
    KeycloakAngularModule,
    RouterModule.forChild(remoteRoutes),
    SharedComponentsModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    AsyncPipe,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true
    },
    {
      provide: KEYCLOAK_GUARD_CONFIG,
      useValue: {
        clientId: 'product-service' //TODO
      },

    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MockInterceptor,
      multi: true,
    },
    KeycloakGuard,
    KeycloakService,
    // UserService
  ],
})
export class RemoteEntryModule {
}
