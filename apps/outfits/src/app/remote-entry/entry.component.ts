import {ChangeDetectorRef, Component} from '@angular/core';
import {
  OutfitCreatorComponent,
  ProductAddedResult,
  ProductModalComponent,
  ProductModalData
} from "@outfit-planner-mf/shared/components";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "@outfit-planner-mf/shared/auth";


@Component({
  selector: 'outfit-planner-mf-outfits-entry',
  templateUrl: './entry.component.html'
})
export class RemoteEntryComponent {


  constructor(private userService: UserService) {
  }


}
