import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {UserService} from "@outfit-planner-mf/shared/auth";
import {Router, RouterStateSnapshot} from "@angular/router";
import {distinctUntilChanged, from, map, Observable, of, shareReplay, switchMap} from "rxjs";
import {MenuItem} from "primeng/api";
import {Tooltip} from "primeng/tooltip";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'outfit-planner-mf-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements OnInit, AfterViewInit {
  isLoggedIn$: Observable<boolean> = this.userService.isUserLoggedIn$;
  items: Observable<MenuItem[]>;
  @ViewChild('loginTooltip') loginTooltip: NgbTooltip | null = null;

  constructor(private userService: UserService, private router: Router) {

    this.items = this.isLoggedIn$.pipe(map((isLoggedIn) => this.getItems(isLoggedIn)))

  }

  getItems(isLoggedIn: boolean): MenuItem[] {
    if (isLoggedIn) {
      return [
        {
          label: 'Log out',
          command: () => {
            this.userService.logOut();
          },
        }
      ]
    }
    return [
      {
        label: 'Log in',
        command: () => {
          this.userService.logIn()
        },
      },
    ]
  }


  ngOnInit() {

    // this.isLoggedIn$
    //   .pipe(distinctUntilChanged())
    //   .subscribe(async (loggedIn) => {
    //     // Queue the navigation after initialNavigation blocking is completed
    //     setTimeout(() => {
    //         // if (!loggedIn) {
    //         //   this.router.navigateByUrl('outfits');
    //         // } else {
    //         //   this.router.navigateByUrl('');
    //       }
    //     );
    //   });
  }

  ngAfterViewInit(): void {
    this.isLoggedIn$.subscribe((isLoggeed) => {
      isLoggeed ? this.closeTooltip() : this.openTooltip();
    })
    // this.openTooltip();
  }


  // return this.keycloakAuthService.keycloakLogin(this.router.routerState.snapshot);


  triggerTooltip() {
    this.isLoggedIn$.subscribe((isLoggedIn) => {
      if(isLoggedIn){
        this.closeTooltip();
        return
      }
      this.loginTooltip?.isOpen() ? this.closeTooltip() : this.openTooltip();
    })
  }

  private openTooltip() {
    return this.loginTooltip?.open();
  }

  private closeTooltip() {
    return this.loginTooltip?.close();
  }
}
