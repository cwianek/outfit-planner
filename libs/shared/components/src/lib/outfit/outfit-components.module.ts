import {NgModule} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MenuModule} from "primeng/menu";
import {CommonModule} from "@angular/common";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {ProductComponentsModule} from "../product";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {OutfitCreatorComponent} from "./outfit-creator/outfit-creator.component";
import {OutfitPreviewComponent} from "./outfit-preview/outfit-preview.component";

@NgModule({
  imports: [
    ButtonModule,
    MatIconModule,
    RouterLink,
    RouterLinkActive,
    MenuModule,
    CommonModule,
    NgbTooltip,
    ProductComponentsModule,
    MatDialogModule,
    MatButtonModule
  ],
  declarations: [
    OutfitCreatorComponent,
    OutfitPreviewComponent
  ],
  exports: [
    OutfitCreatorComponent,
    OutfitPreviewComponent
  ],
  providers: [],
})
export class OutfitComponentsModule {}
