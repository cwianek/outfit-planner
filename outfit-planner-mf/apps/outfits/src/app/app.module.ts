import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import {SharedComponentsModule} from "@outfit-planner-mf/shared/components";
import {provideAnimations} from "@angular/platform-browser/animations";
import {KEYCLOAK_GUARD_CONFIG} from "@outfit-planner-mf/shared/auth";

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
            {initialNavigation: 'enabledBlocking'}
        ),
        SharedComponentsModule,
    ],
  exports: [
  ],
  providers: [
    provideAnimations(),
    {
      provide: KEYCLOAK_GUARD_CONFIG,
      useValue: {
        clientId: 'outfit-service'
      },

    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
