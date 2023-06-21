import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {UserTicket} from "../model/user-ticket";
import {UserTicketService} from "../services/user-ticket.service";

@Injectable({
  providedIn: 'root'
})
export class MyTicketsResolver implements Resolve<UserTicket[]> {

  constructor(private readonly userTicketService: UserTicketService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserTicket[]> {
    return this.userTicketService.getActiveTickets();
  }
}
