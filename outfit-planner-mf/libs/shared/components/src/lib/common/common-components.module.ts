import {NgModule} from '@angular/core';
import {MessageCardComponent} from "./message-card/message-card.component";
import {MessagesModule} from "primeng/messages";


@NgModule({
  imports: [
    MessagesModule
  ],
  declarations: [
    MessageCardComponent
  ],
  exports: [
    MessageCardComponent,
  ],
  providers: [],
})
export class CommonComponentsModule {
}
