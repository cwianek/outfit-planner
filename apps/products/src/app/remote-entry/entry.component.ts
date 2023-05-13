import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {UserService} from "@outfit-planner-mf/shared/auth";

@Component({
  selector: 'outfit-planner-mf-products-entry',
  templateUrl: './entry.component.html'
})
export class RemoteEntryComponent implements OnDestroy{

  constructor(private cd: ChangeDetectorRef) {
  }

  ngOnDestroy(): void {
    this.cd.detectChanges();
  }

}
