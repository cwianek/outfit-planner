import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';

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
