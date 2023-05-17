import {Component, Input, OnInit} from '@angular/core';
import {UserTicket} from "../../../model/user-ticket";

@Component({
  selector: 'bs-my-tickets-history',
  templateUrl: './my-tickets-history.component.html',
  styleUrls: ['./my-tickets-history.component.scss']
})
export class MyTicketsHistoryComponent implements OnInit {

  @Input() userTickets: UserTicket[] = [];

  constructor() {}

  ngOnInit(): void {}

  getUserHistoryTickets(): UserTicket[] {
    return this.userTickets.filter(ticket => ticket.validation !== null);
  }

}
