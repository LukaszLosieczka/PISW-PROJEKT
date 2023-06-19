import {Component} from '@angular/core';
import {UserTicket} from "../../model/user-ticket";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'bs-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.scss']
})
export class MyTicketsComponent {

  myTickets: UserTicket[];
  historyTickets: UserTicket[];

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.myTickets = activatedRoute.snapshot.data['myTickets'];
    this.historyTickets = activatedRoute.snapshot.data['historyTickets'];
  }

}
