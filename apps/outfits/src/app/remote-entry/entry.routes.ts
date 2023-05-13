import {Route} from '@angular/router';
import {RemoteEntryComponent} from './entry.component';
import {KeycloakGuard} from "@outfit-planner-mf/shared/auth";

export const remoteRoutes: Route[] = [
  {path: '', component: RemoteEntryComponent, canActivate: [KeycloakGuard], resolve: []},
];
