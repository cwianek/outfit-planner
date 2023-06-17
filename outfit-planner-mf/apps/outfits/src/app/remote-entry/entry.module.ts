import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

import {RemoteEntryComponent} from './entry.component';
import {remoteRoutes} from './entry.routes';
import {MatCardModule} from "@angular/material/card";
import {KeycloakAngularModule, KeycloakBearerInterceptor, KeycloakService} from "keycloak-angular";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {KEYCLOAK_GUARD_CONFIG, KeycloakGuard, MockInterceptor} from "@outfit-planner-mf/shared/auth";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {CommonComponentsModule, SharedComponentsModule} from "@outfit-planner-mf/shared/components";
import {CarouselModule} from "primeng/carousel";
import {ButtonModule} from "primeng/button";
import {MenuModule} from "primeng/menu";
import {ToastModule} from "primeng/toast";
import {OutfitsCarouselContainerComponent} from "../outfits-carousel-container/outfits-carousel-container.component";
import {OutfitsCarouselComponent} from "../outfits-carousel/outfits-carousel.component";

@NgModule({
  declarations: [
    RemoteEntryComponent,
    OutfitsCarouselContainerComponent,
    OutfitsCarouselComponent
  ],
    imports: [CommonModule,
        RouterModule.forChild(remoteRoutes),
        MatCardModule,
        KeycloakAngularModule,
        MatIconModule,
        MatButtonModule,
        SharedComponentsModule,
        HttpClientModule,
        CarouselModule,
        ButtonModule,
        MenuModule,
        ToastModule, CommonComponentsModule
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
        clientId: 'outfit-service'
      },
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MockInterceptor,
      multi: true,
    },
    KeycloakGuard,
    KeycloakService
  ],
})

export class RemoteEntryModule {
}
