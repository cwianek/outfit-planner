import {AfterViewInit, Component, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {first, map, Observable, Subject} from "rxjs";
import {MenuItem} from "primeng/api";
import {UserService} from "@outfit-planner-mf/shared/auth";
import {Router} from "@angular/router";


@Component({
  selector: 'outfit-planner-mf-navigation-login-in',
  templateUrl: './navigation-log-in.component.html',
  styleUrls: ['./navigation-log-in.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavigationLogInComponent {

  @ViewChild('loginTooltip') loginTooltip: NgbTooltip | null = null;

  isLoggedIn$: Observable<boolean> = this.userService.isUserLoggedIn$;

  menuItems$: Observable<MenuItem[]>;

  constructor(private userService: UserService,
              public router: Router) {
    this.menuItems$ = this.isLoggedIn$
      .pipe(map((isLoggedIn) => this.getItems(isLoggedIn)))
  }

  private getItems(isLoggedIn: boolean): MenuItem[] {
    const loggedInItems = [
      {
        label: 'Log out',
        command: () => {
          this.userService.logOut();
        },
      }
    ]

    const notLoggedInItems = [
      {
        label: 'Log in',
        command: () => {
          this.userService.logIn()
        },
      },
    ]

    return isLoggedIn ? loggedInItems : notLoggedInItems
  }

}

