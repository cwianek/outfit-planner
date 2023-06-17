import {ChangeDetectorRef, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {
  CreateOutfitRequest,
  Geolocation,
  LocationService,
  Outfit,
  OutfitCreatorComponent,
  OutfitsService,
  PredictOutfitsRequest,
  Product,
  ProductsService
} from "@outfit-planner-mf/shared/components";

import {filter, first, Observable, of, switchMap} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {UserService} from "@outfit-planner-mf/shared/auth";

@Component({
  selector: 'outfit-planner-mf-outfits-carousel-container',
  templateUrl: './outfits-carousel-container.component.html',
  styleUrls: ['./outfits-carousel-container.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitsCarouselContainerComponent implements OnInit {

  outfits$: Observable<Outfit[]> = new Observable<Outfit[]>();

  isLoggedIn$: Observable<boolean> = this.userService.isUserLoggedIn$;

  messageText = 'These are sample outfits, log in to add your own.'

  constructor(
    private outfitsService: OutfitsService,
    private router: Router,
    private locationService: LocationService,
    private cd: ChangeDetectorRef,
    private dialog: MatDialog,
    private productsService: ProductsService,
    private userService: UserService) {
  }

  ngOnInit(): void {
    this.outfits$ = this.getLocation().pipe(
      switchMap((geolocation) => {
        const request: PredictOutfitsRequest = {
          geolocation
        }
        return this.outfitsService.predictOutfits(request)
      })
    )
  }

  openOutfitCreatorModal = (): void => {
    this.fetchProducts()
      .subscribe(this.openDialog)
  }

  private getLocation = (): Observable<Geolocation> => {
    return this.userService.isUserLoggedIn$.pipe(
      first(),
      switchMap((isLoggedIn) => {
        if (isLoggedIn) {
          return this.locationService.getLocation();
        } else {
          return of(this.getDefaultGeolocation())
        }
      })
    )
  }

  private getDefaultGeolocation = (): Geolocation => {
    return {
      latitude: 52.409538,
      longitude: 16.931992
    }
  }

  private fetchProducts = (): Observable<Product[]> => {
    return this.productsService.getProducts();
  }

  private openDialog = (products: Product[]): void => {
    const dialogRef = this.dialog.open(OutfitCreatorComponent, {
      data: {
        products: products
      },
      minWidth: '350px',
      maxWidth: '700px'
    });

    dialogRef.afterClosed()
      .pipe(filter((result) => result))
      .subscribe(this.addOutfit);
  }


  private addOutfit = (outfit: Outfit) => {
    const request: CreateOutfitRequest = {
      productsIds: outfit.products.map((product) => product.id)
    }
    this.outfitsService.addOutfit(request)
      .subscribe();
  }

}
