import {Component, Input, OnInit} from '@angular/core';
import {Ticket} from "../../../model/ticket";
import {TicketType} from "../../../model/ticket-type";
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'bs-tickets-list-table',
  templateUrl: './tickets-list-table.component.html',
  styleUrls: ['./tickets-list-table.component.scss']
})
export class TicketsListTableComponent implements OnInit {

  @Input() tickets: Ticket[] = []

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

  getSingleTickets(): Ticket[]{
    return this.tickets.filter(t => t.type === TicketType.Single);
  }

  getSeasonTickets(): Ticket[]{
    return this.tickets.filter(t => t.type === TicketType.Season);
  }

  getTimeTickets(): Ticket[]{
    return this.tickets.filter(t => t.type === TicketType.Time);
  }


}
