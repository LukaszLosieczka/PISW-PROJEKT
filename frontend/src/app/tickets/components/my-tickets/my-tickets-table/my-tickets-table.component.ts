import {Component, Input, OnInit} from '@angular/core';
import {UserTicket} from "../../../model/user-ticket";

@Component({
  selector: 'bs-my-tickets-table',
  templateUrl: './my-tickets-table.component.html',
  styleUrls: ['./my-tickets-table.component.scss']
})
export class MyTicketsTableComponent implements OnInit {

  @Input() userTickets: UserTicket[] = [];

  constructor() {}

  ngOnInit(): void {}

  getUserTickets(): UserTicket[] {
    return this.userTickets.filter(ticket => ticket.validation === null);
  }

}
