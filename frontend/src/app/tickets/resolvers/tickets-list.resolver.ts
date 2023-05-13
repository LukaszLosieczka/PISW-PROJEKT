import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {TicketService} from "../services/ticket.service";
import {Ticket} from "../model/ticket";

@Injectable({
  providedIn: 'root'
})
export class TicketsListResolver implements Resolve<Ticket[]> {

  constructor(private readonly ticketService: TicketService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ticket[]> {
    return this.ticketService.getAllTickets();
  }
}
