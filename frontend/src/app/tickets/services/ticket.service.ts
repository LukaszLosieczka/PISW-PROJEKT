import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {Ticket} from "../model/ticket";

const ticketsApiPrefix = '/api/tickets';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private readonly http: HttpClient) { }

  getAllTickets(): Observable<Ticket[]>{
    return this.http.get<Ticket[]>(ticketsApiPrefix);
  }
}
