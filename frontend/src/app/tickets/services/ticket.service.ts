import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ticket} from "../model/ticket";
import {environment} from "../../../environments/environment";

const ticketsApiPrefix = `${environment.apiUrl}tickets/available_tickets`;

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private readonly http: HttpClient) {}

  getAllTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(ticketsApiPrefix);
  }

  getTicket(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${ticketsApiPrefix}/${id}`);
  }

}
