import {Component, Input} from '@angular/core';
import {UserTicket} from "../../../model/user-ticket";

@Component({
  selector: 'bs-my-tickets-table',
  templateUrl: './my-tickets-table.component.html',
  styleUrls: ['./my-tickets-table.component.scss']
})
export class MyTicketsTableComponent {

  @Input() userTickets: UserTicket[] = [];

  constructor() {}

  getUserTickets(): UserTicket[] {
    return this.userTickets;
  }

}
