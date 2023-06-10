import {AfterViewInit, Component, OnDestroy, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {NavigationBarButton} from "@outfit-planner-mf/shared/components";

@Component({
  selector: 'outfit-planner-mf-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavigationBarComponent {
  @ViewChild('loginTooltip') loginTooltip: NgbTooltip | null = null;

  navigationItems: NavigationBarButton[] = [
    {
      label: 'Dashboard',
      icon: 'fa-house',
      link: ['./']
    },
    {
      label: 'Products',
      icon: 'fa-tags',
      link: 'products'
    },
    {
      label: 'Outfits',
      icon: 'fa-shirt',
      link: 'outfits'
    },
  ]


}

