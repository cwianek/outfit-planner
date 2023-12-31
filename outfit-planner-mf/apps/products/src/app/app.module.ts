import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {AppComponent} from './app.component';
import {provideAnimations} from "@angular/platform-browser/animations";
import {KEYCLOAK_GUARD_CONFIG} from "@outfit-planner-mf/shared/auth";
import {NavigationComponentsModule} from "@outfit-planner-mf/shared/components";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    RouterModule.forRoot(
      [
        {
          path: '',
          loadChildren: () =>
            import('./remote-entry/entry.module').then(
              (m) => m.RemoteEntryModule
            ),
        },
      ],
      {initialNavigation: 'enabledNonBlocking'}
    ),
    NavigationComponentsModule,
  ],
  providers: [
    provideAnimations(),
    {
      provide: KEYCLOAK_GUARD_CONFIG,
      useValue: {
        clientId: 'product-service'
      },

    },
  ],
  bootstrap: [AppComponent],
  exports: [],
})
export class AppModule {
}
