import { NxWelcomeComponent } from './nx-welcome.component';
import { Route } from '@angular/router';
import { loadRemoteModule } from '@nrwl/angular/mf';

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
    component: NxWelcomeComponent,
  },
];
