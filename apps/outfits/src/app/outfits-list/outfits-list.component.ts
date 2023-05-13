import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  Geolocation,
  LocationService,
  Outfit,
  OutfitsService,
  ToggleWearOutfitRequest
} from '@outfit-planner-mf/shared/components';
import {NgbCarousel} from "@ng-bootstrap/ng-bootstrap";
import {map, switchMap} from "rxjs";

@Component({
  selector: 'outfit-planner-mf-outfits-list',
  templateUrl: './outfits-list.component.html',
  styleUrls: ['./outfits-list.component.scss'],
})
export class OutfitsListComponent implements OnInit {
  @Input() outfits: Outfit[] = [];

  @ViewChild('carousel', {static: true}) carousel: NgbCarousel | undefined;

  responsiveOptions: any[] = [];

  clicked: { [key: string]: boolean } = {}

  constructor(private outfitService: OutfitsService, private locationService: LocationService) {

  }


  ngOnInit(): void {
    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 5,
        numScroll: 1,
      },

    ];
  }

  toggleSelection(outfit: Outfit) {
    outfit.wornToday = !outfit.wornToday
    this.locationService.getLocation().pipe(
      map((geolocation) => this.createRequest(geolocation, outfit.id)),
      switchMap((request) => this.outfitService.toggleWear(request))
    ).subscribe((worn) => {
      outfit.wornToday = worn;
    });
  }

  private createRequest = (geolocation: Geolocation, id: string): ToggleWearOutfitRequest => {
    return {
      id: id,
      geolocation: geolocation
    }
  }

  unSelectOutfit(id: string) {

  }
}
