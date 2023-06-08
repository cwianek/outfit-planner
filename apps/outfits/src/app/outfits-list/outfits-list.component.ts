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
import {map, switchMap} from "rxjs";
import {MenuItem} from "primeng/api";
import {UserService} from "@outfit-planner-mf/shared/auth";

@Component({
  selector: 'outfit-planner-mf-outfits-list',
  templateUrl: './outfits-list.component.html',
  styleUrls: ['./outfits-list.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitsListComponent implements OnInit, OnChanges, AfterViewInit {
  @Input() outfits: Outfit[] = [];

  @Output() newOutfitClicked: EventEmitter<void> = new EventEmitter<void>();

  @ViewChild('carousel', {static: true}) carousel: NgbCarousel | undefined;
  @ViewChild('loginTooltip') loginTooltip: NgbTooltip | null = null;

  orderedOutfits: Outfit[] = [];
  isLoggedIn$ = this.userService.isUserLoggedIn$;
  responsiveOptions: any[] = [];

  clicked: { [key: string]: boolean } = {}
  items: MenuItem[];

  constructor(private outfitService: OutfitsService, private locationService: LocationService, private userService: UserService) {
    this.items = [
      {
        label: 'New',
        icon: 'pi pi-fw pi-plus',
        command: () => {
          this.newOutfitClicked.emit();
        }
      }
    ];
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

  onMoreActionClick() {

  }

  ngAfterViewInit(): void {
      // this.isLoggedIn$.subscribe((isLoggedIn) => {
      //   if(isLoggedIn){
      //     this.closeTooltip();
      //     return
      //   }
      //   this.loginTooltip?.isOpen() ? this.closeTooltip() : this.openTooltip();
      // })
    }

  private openTooltip() {
    return this.loginTooltip?.open();
  }

  private closeTooltip() {
    return this.loginTooltip?.close();
  }
}
