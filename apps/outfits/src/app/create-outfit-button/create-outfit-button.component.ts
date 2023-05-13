import {ChangeDetectorRef, Component} from '@angular/core';
import {
  CreateOutfitRequest,
  Outfit,
  OutfitCreatorComponent,
  OutfitCreatorModalData, OutfitsService, Product,
  ProductAddedResult,
  ProductsService
} from "@outfit-planner-mf/shared/components";
import {MatDialog} from "@angular/material/dialog";
import {Observable, shareReplay} from "rxjs";

@Component({
  selector: 'outfit-planner-mf-create-outfit-button',
  templateUrl: './create-outfit-button.component.html',
  styleUrls: ['./create-outfit-button.component.scss'],
})
export class CreateOutfitButtonComponent {

  products$: Observable<Product[]> | null = null;

  constructor(private cd: ChangeDetectorRef, private dialog: MatDialog, private productsService: ProductsService, private outfitsService: OutfitsService) {
  }

  fetchProducts = (): Observable<Product[]> => {
    if (this.products$ == null) {
      this.products$ = this.productsService.getProducts().pipe(shareReplay(1))
    }
    return this.products$;
  }

  openDialog = (products: Product[]): void => {
    const modalData: OutfitCreatorModalData = {
      products: products
    }
    const dialogRef = this.dialog.open(OutfitCreatorComponent, {
      data: modalData,
      minWidth: '700px',
    });

    dialogRef.afterClosed().subscribe((result: Outfit | null) => {
      if (!result) {
        return;
      }
      this.addOutfit(result);
    });

  }

  addOutfit(outfit: Outfit){
    const request: CreateOutfitRequest = {
      productsIds: outfit.products.map((product) => product.id)
    }
    this.outfitsService.addOutfit(request).subscribe();
  }

  openOutfitCreatorModal = (): void => {
    this.fetchProducts().subscribe(this.openDialog)
  }
}

