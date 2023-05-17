import {Component} from '@angular/core';
import {UserService} from "../../../shared/services/user.service";
import {UserTicket} from "../../model/user-ticket";

@Component({
  selector: 'bs-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.scss']
})
export class MyTicketsComponent {

  userTickets: UserTicket[] = [];

  constructor(private userService: UserService) {
    this.userTickets = this.userService.userTickets;
  }

}
