import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {UserTicketService} from "../services/user-ticket.service";
import {UserTicket} from "../model/user-ticket";

@Injectable({
  providedIn: 'root'
})
export class HistoryTicketsResolver implements Resolve<UserTicket[]> {
  constructor(private readonly userTicketService: UserTicketService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserTicket[]> {
    return this.userTicketService.getHistoryTickets();
  }
}
