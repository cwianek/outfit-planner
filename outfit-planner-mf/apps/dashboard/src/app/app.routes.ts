import { Route } from '@angular/router';
import { loadRemoteModule } from '@nrwl/angular/mf';
import {KeycloakGuard} from "@outfit-planner-mf/shared/auth";
import {DashboardViewComponent} from "./dashboard-view/dashboard-view.component";

export const appRoutes: Route[] = [
  {
    path: 'outfits',
    loadChildren: () =>
      loadRemoteModule('outfits', './Module').then((m) => m.RemoteEntryModule),
  },

  {
    path: 'products',
    loadChildren: () =>
      loadRemoteModule('products', './Module').then((m) => m.RemoteEntryModule),
  },

  {
    path: '',
    component: DashboardViewComponent,
    canActivate: [KeycloakGuard]
  },
];
