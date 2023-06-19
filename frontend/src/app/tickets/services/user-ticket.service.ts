import {Injectable} from '@angular/core';
import {Ticket} from "../model/ticket";
import {Discount} from "../model/discount";
import {UserTicket} from "../model/user-ticket";
import {TicketType} from "../model/ticket-type";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

const myTicketsApiPrefix = 'http://localhost:8080/tickets';

@Injectable({
  providedIn: 'root'
})
export class UserTicketService {

  userTickets: UserTicket[] = [];
  userTicket: UserTicket;
  Ticket: Ticket;
  Discount: Discount | null;
  Quantity: number;

  constructor(private readonly http: HttpClient, private router: Router) {}

  getHistoryTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${myTicketsApiPrefix}/ticketshistory`);
  }

  getActiveTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${myTicketsApiPrefix}/mytickets`);
  }
  addNewUserTickets(): void {

  }

  checkUserTicketExists(userTicketCode: number): boolean {
    return this.userTickets.some(ticket => ticket.code === Number(userTicketCode));
  }

  checkUserTicketHasVehicle(userTicketCode: number, vehicle: number): boolean {
    return false;
  }

}
