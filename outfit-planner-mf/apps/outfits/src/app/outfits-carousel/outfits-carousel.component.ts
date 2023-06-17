import {
  AfterViewInit,
  Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {
  Geolocation,
  LocationService,
  Outfit,
  OutfitsService,
  ToggleWearOutfitRequest
} from '@outfit-planner-mf/shared/components';
import {NgbCarousel, NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {filter, first, map, Observable, switchMap} from "rxjs";
import {MenuItem} from "primeng/api";
import {UserService} from "@outfit-planner-mf/shared/auth";

@Component({
  selector: 'outfit-planner-mf-outfits-carousel',
  templateUrl: './outfits-carousel.component.html',
  styleUrls: ['./outfits-carousel.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitsCarouselComponent implements OnChanges {

  @ViewChild('carousel', {static: true}) carousel: NgbCarousel | undefined;

  @Input() outfits: Outfit[] = [];

  @Output() newOutfitCreation: EventEmitter<void> = new EventEmitter<void>();

  isLoggedIn$ = this.userService.isUserLoggedIn$;

  orderedOutfits: Outfit[] = [];

  items: MenuItem[] = [
    {
      label: 'New',
      icon: 'pi pi-fw pi-plus',
      command: () => {
        this.newOutfitCreation.emit();
      }
    }
  ]

  constructor(
    private outfitService: OutfitsService,
    private locationService: LocationService,
    private userService: UserService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const outfits = changes['outfits'];
    if (outfits && outfits.currentValue) {
      this.orderedOutfits = this.prepareOutfits(outfits.currentValue)
    }
  }

  private prepareOutfits(outfits: Outfit[]) {
    return outfits
      .map(this.roundProbabilities)
      .sort(this.sortByProbability);
  }

  private sortByProbability = (o1: Outfit, o2: Outfit) => {
    return (o2.matchProbability || 0) - (o1.matchProbability || 0);
  }

  private roundProbabilities = (outfit: Outfit) => {
    const _outfit = {...outfit};
    _outfit.matchProbability = Math.round((outfit.matchProbability || 0) * 100)
    return _outfit;
  }

  toggleSelection(outfit: Outfit) {
    outfit.wornToday = !outfit.wornToday
    this.userService.isUserLoggedIn$.pipe(
      first(),
      filter((isLogged) => isLogged),
      switchMap(() => this.toggleWear(outfit))
    ).subscribe((worn) => {
      outfit.wornToday = worn;
    })
  }

  private toggleWear = (outfit: Outfit): Observable<boolean> => {
    return this.locationService.getLocation().pipe(
      map((geolocation) => this.createRequest(geolocation, outfit.id)),
      switchMap((request) => this.outfitService.toggleWear(request))
    );
  }

  private createRequest = (geolocation: Geolocation, id: string): ToggleWearOutfitRequest => {
    return {
      id: id,
      geolocation: geolocation
    }
  }

}
