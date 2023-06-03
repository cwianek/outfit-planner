import {AfterViewInit, ChangeDetectorRef, Component} from '@angular/core';
import {
  CreateOutfitRequest,
  LocationService,
  Outfit, OutfitCreatorComponent, OutfitCreatorModalData,
  OutfitsService,
  PredictOutfitsRequest,
  Product, ProductsService
} from "@outfit-planner-mf/shared/components";

import {Observable, shareReplay, switchMap, tap} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "@outfit-planner-mf/shared/auth";

@Component({
  selector: 'outfit-planner-mf-outfits-list-container',
  templateUrl: './outfits-list-container.component.html',
  styleUrls: ['./outfits-list-container.component.scss'],
})
export class OutfitsListContainerComponent implements AfterViewInit{

  outfits$: Observable<Outfit[]> = new Observable<Outfit[]>();
  products$: Observable<Product[]> | null = null;

  constructor(private outfitsService: OutfitsService, private router: Router, private locationService: LocationService, private cd: ChangeDetectorRef, private dialog: MatDialog, private productsService: ProductsService, private activatedRoute: ActivatedRoute, private userService: UserService) {
  }

  ngAfterViewInit(): void {
    this.outfits$ = this.locationService.getLocation().pipe(
      tap(() => console.log("CO TO MA BYC")),
      switchMap((geolocation) => {
        const request: PredictOutfitsRequest = {
          geolocation
        }
        return this.outfitsService.predictOutfits(request)
      })
    )
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
      minWidth: '500px',
      // minHeight: '700px',
      maxWidth: '700px'
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
