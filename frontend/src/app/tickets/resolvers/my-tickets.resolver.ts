import {Injectable} from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import {Observable} from 'rxjs';
import {TicketService} from "../services/ticket.service";
import {Ticket} from "../model/ticket";

@Injectable({
  providedIn: 'root'
})
export class MyTicketsResolver implements Resolve<Ticket[]> {

  constructor(private readonly ticketService: TicketService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ticket[]> {
    return this.ticketService.getUserTickets();
  }

}
