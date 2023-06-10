import {NgModule} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MenuModule} from "primeng/menu";
import {CommonModule} from "@angular/common";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {ProductCategoriesListComponent} from "./product-categories-list/product-categories-list.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatCardModule} from "@angular/material/card";
import {ProductItemComponent} from "./product-item/product-item.component";
import {ProductModalComponent} from "./product-modal/product-modal.component";
import {ProductsListComponent} from "./products-list/products-list.component";

@NgModule({
  imports: [
    ButtonModule,
    MatIconModule,
    RouterLink,
    RouterLinkActive,
    MenuModule,
    CommonModule,
    NgbTooltip,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatCardModule
  ],
  declarations: [
    ProductCategoriesListComponent,
    ProductItemComponent,
    ProductModalComponent,
    ProductsListComponent
  ],
  exports: [
    ProductCategoriesListComponent,
    ProductItemComponent,
    ProductModalComponent,
    ProductsListComponent
  ],
  providers: [],
})
export class ProductComponentsModule {
}
