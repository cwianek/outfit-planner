import {Injector, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductModalComponent} from './product-modal/product-modal.component';
import {ProductItemComponent} from './product-item/product-item.component';
import {NgbCarousel, NgbDropdownModule, NgbModalModule, NgbSlide} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ProductsListComponent} from './products-list/products-list.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {OutfitCreatorComponent} from './outfit-creator/outfit-creator.component';
import {ProductsService} from './services/products.service';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollableWindow,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import {OutfitPreviewComponent} from './outfit-preview/outfit-preview.component';
import {OutfitsService} from './services/outfits.service';
import {KnobModule} from "primeng/knob";
import {TabMenuModule} from "primeng/tabmenu";
import {ProductCategoriesListComponent} from "./product-categories-list/product-categories-list.component";
import {ButtonModule} from "primeng/button";

@NgModule({
  imports: [
    CommonModule,
    NgbDropdownModule,
    ReactiveFormsModule,
    NgbModalModule,
    MatSlideToggleModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatCardModule,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    CdkVirtualForOf,
    CdkVirtualScrollableWindow,
    NgbCarousel,
    NgbSlide,
    KnobModule,
    TabMenuModule,
    ButtonModule,
  ],
  declarations: [
    ProductModalComponent,
    ProductItemComponent,
    ProductsListComponent,
    OutfitCreatorComponent,
    OutfitPreviewComponent,
    ProductCategoriesListComponent
  ],
  exports: [ProductItemComponent, ProductModalComponent, ProductsListComponent, OutfitPreviewComponent],
  providers: [
    {
      provide: MatDialogRef,
      useValue: {},
    },
    ProductsService,
    OutfitsService,
  ],
})
export class SharedComponentsModule {
  static injector: Injector;

  constructor(injector: Injector) {
    SharedComponentsModule.injector = injector;
  }
}
