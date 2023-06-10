import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import {SharedComponentsModule} from "@outfit-planner-mf/shared/components";

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
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
