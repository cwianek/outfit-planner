import {Component, ViewEncapsulation} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from "@angular/router";

export interface NavigationEvent {
  instance: any;
  showSpinner: boolean;
}

@Component({
  selector: 'outfit-planner-mf-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  showSpinner = false;

  navigationEvents: NavigationEvent[] = [
    {instance: NavigationStart, showSpinner: true},
    {instance: NavigationEnd, showSpinner: false},
    {instance: NavigationError, showSpinner: false},
    {instance: NavigationCancel, showSpinner: false},
  ]

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      this.navigationEvents.some((navigationEvent) => {
        if (event instanceof navigationEvent.instance) {
          this.showSpinner = navigationEvent.showSpinner;
        }
      })
    });
  }

}
