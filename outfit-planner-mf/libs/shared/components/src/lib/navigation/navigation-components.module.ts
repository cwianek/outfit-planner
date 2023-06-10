import {NgModule} from '@angular/core';
import {NavigationButtonComponent} from "./navigation-button/navigation-button.component";
import {ButtonModule} from "primeng/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MenuModule} from "primeng/menu";
import {CommonModule} from "@angular/common";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {NavigationLogInComponent} from './navigation-log-in-button/navigation-log-in.component';
import {ProgressSpinnerModule} from "primeng/progressspinner";

@NgModule({
  imports: [
    ButtonModule,
    MatIconModule,
    RouterLink,
    RouterLinkActive,
    MenuModule,
    CommonModule,
    NgbTooltip,
    ProgressSpinnerModule,
  ],
  declarations: [
    NavigationButtonComponent,
    NavigationLogInComponent,
  ],
  exports: [
    NavigationButtonComponent,
    NavigationLogInComponent,
  ],
  providers: [],
})
export class NavigationComponentsModule {
}
