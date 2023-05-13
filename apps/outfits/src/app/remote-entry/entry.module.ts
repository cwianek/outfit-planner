import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

import {RemoteEntryComponent} from './entry.component';
import {NxWelcomeComponent} from './nx-welcome.component';
import {remoteRoutes} from './entry.routes';
import {MatCardModule} from "@angular/material/card";
import {KeycloakAngularModule, KeycloakBearerInterceptor} from "keycloak-angular";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuard} from "@outfit-planner-mf/shared/auth";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SharedComponentsModule} from "@outfit-planner-mf/shared/components";
import {CreateOutfitButtonComponent} from "../create-outfit-button/create-outfit-button.component";
import {OutfitsListContainerComponent} from "../outfits-list-container/outfits-list-container.component";
import {CarouselModule} from "primeng/carousel";
import {ButtonModule} from "primeng/button";
import {OutfitsListComponent} from "../outfits-list/outfits-list.component";

@NgModule({
  declarations: [RemoteEntryComponent, NxWelcomeComponent, CreateOutfitButtonComponent, OutfitsListContainerComponent, OutfitsListComponent],
  imports: [CommonModule, RouterModule.forChild(remoteRoutes), MatCardModule, KeycloakAngularModule, MatIconModule, MatButtonModule, SharedComponentsModule, HttpClientModule, CarouselModule, ButtonModule],
  providers: [
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
    KeycloakGuard
  ],
})

export class RemoteEntryModule {
}
