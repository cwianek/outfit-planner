import {AfterViewInit, Component} from '@angular/core';
import {LocationService, Outfit, OutfitsService, PredictOutfitsRequest} from "@outfit-planner-mf/shared/components";
import {Observable, switchMap, tap} from "rxjs";

@Component({
  selector: 'outfit-planner-mf-outfits-list-container',
  templateUrl: './outfits-list-container.component.html',
  styleUrls: ['./outfits-list-container.component.scss'],
})
export class OutfitsListContainerComponent implements AfterViewInit{

  outfits$: Observable<Outfit[]> = new Observable<Outfit[]>();

  constructor(private outfitsService: OutfitsService, private locationService: LocationService) {
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

}
