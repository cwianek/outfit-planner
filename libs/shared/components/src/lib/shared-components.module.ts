import {Injector, NgModule} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {ProductsService} from './services/products.service';
import {OutfitsService} from './services/outfits.service';
import {NavigationComponentsModule} from "./navigation";
import {ProductComponentsModule} from "./product";
import {OutfitComponentsModule} from "./outfit";

@NgModule({
  imports: [
    ProductComponentsModule,
    OutfitComponentsModule,
    NavigationComponentsModule
  ],
  declarations:
    [],
  exports: [NavigationComponentsModule, ProductComponentsModule, OutfitComponentsModule],
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
