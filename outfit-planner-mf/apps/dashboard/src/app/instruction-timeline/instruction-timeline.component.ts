import {Component, HostListener, OnInit, ViewEncapsulation} from '@angular/core';
import {ScreenResolutions} from "@outfit-planner-mf/shared/components";

@Component({
  selector: 'outfit-planner-mf-instruction-timeline',
  templateUrl: './instruction-timeline.component.html',
  styleUrls: ['./instruction-timeline.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class InstructionTimelineComponent implements OnInit {
  @HostListener('window:resize', [])
  onWindowResize() {
    this.timelineAlign = window.innerWidth > ScreenResolutions.Large ? 'left' : 'left'
  }

  timelineAlign = 'alternate'

  events: any[];

  constructor() {
    this.events = [
      {
        number: 1,
        status: 'Add your clothes',
        icon: 'pi pi-shopping-cart',
        description: 'from which you will create outfits'
      },
      {
        number: 2,
        status: 'Create outfits',
        icon: 'pi pi-shopping-cart',
        description: 'in different style and for different weather conditions'
      },
      {
        number: 3,
        status: 'See recommendations',
        description: 'we recommend you outfits based on your wearing history and current weather for your location',
      },
    ];
  }

  ngOnInit(): void {
    this.onWindowResize()
  }

}

