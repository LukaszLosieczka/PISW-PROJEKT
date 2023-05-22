import {Component} from '@angular/core';
import {UserTicket} from "../../model/user-ticket";
import {UserTicketService} from "../../services/user-ticket.service";

@Component({
  selector: 'bs-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.scss']
})
export class MyTicketsComponent {

  userTickets: UserTicket[] = [];

  constructor(private userTicketService: UserTicketService) {
    this.userTickets = this.userTicketService.userTickets;
  }

}
