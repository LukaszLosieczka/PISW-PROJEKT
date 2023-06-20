import {Injectable} from '@angular/core';
import {UserTicket} from "../model/user-ticket";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const myTicketsApiPrefix = 'http://localhost:8080/tickets';

@Injectable({
  providedIn: 'root'
})
export class UserTicketService {

  userTickets: UserTicket[] = [];

  constructor(private readonly http: HttpClient) {}

  getHistoryTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${myTicketsApiPrefix}/ticketshistory`);
  }

  getActiveTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${myTicketsApiPrefix}/mytickets`);
  }

  addNewUserTickets(ticketId: number, discountId: number | undefined, quantity: number):Observable<UserTicket>{
    return this.http.post<UserTicket>(`${myTicketsApiPrefix}/buy`,
      {
        'ticketId': ticketId,
        'discountId': discountId,
        'quantity': quantity
      });
  }

  checkUserTicketExists(userTicketCode: number): boolean {
    return this.userTickets.some(ticket => ticket.code === Number(userTicketCode));
  }

  checkUserTicketHasVehicle(userTicketCode: number, vehicle: number): boolean {
    return false;
  }

}
