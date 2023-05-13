import { Route } from '@angular/router';
import {ProductListContainerComponent} from "../product-list-container/product-list-container.component";
import {KeycloakGuard} from "@outfit-planner-mf/shared/auth";

export const remoteRoutes: Route[] = [
  { path: '', component: ProductListContainerComponent,
    canActivate: [KeycloakGuard]
  },
];
