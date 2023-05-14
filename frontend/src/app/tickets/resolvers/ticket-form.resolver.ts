import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {Ticket} from "../model/ticket";
import {TicketService} from "../services/ticket.service";

@Injectable({
  providedIn: 'root'
})
export class TicketFormResolver implements Resolve<Ticket> {

  constructor(private readonly ticketService: TicketService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ticket> {
    return this.ticketService.getTicket(route.params['id']);
  }
}
