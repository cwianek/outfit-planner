import {Component, Input, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'outfit-planner-mf-navigation-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavigationButtonComponent {

  @Input() link: any = null;

  @Input() label: string = ''

  @Input() icon: string = ''

}

