import {AfterViewInit, Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Message} from "primeng/api";
import {MessageCardService} from "../../services/message-card.service";
import {Messages} from "primeng/messages";

@Component({
  selector: 'outfit-planner-mf-message-card',
  templateUrl: './message-card.component.html',
  styleUrls: ['./message-card.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class MessageCardComponent implements OnInit, AfterViewInit {
  @ViewChild('pMessages') messagesRef: Messages | null = null;

  @Input() severity = ''

  @Input() detail = ''

  @Input() id = ''

  messages: Message[] = []

  constructor(private messageCardService: MessageCardService) {
  }

  ngOnInit(): void {
    if (!this.messageCardService.wasClosed(this.id)) {
      this.messages = [
        {severity: this.severity, detail: this.detail}
      ]
    }
  }

  ngAfterViewInit(): void {
    this.messagesRef?.valueChange.subscribe((messages: Message[]) => {
      this.messageCardService.setClosed(this.id);
    })
  }
}

