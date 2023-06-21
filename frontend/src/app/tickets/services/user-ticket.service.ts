import {Injectable} from '@angular/core';
import {UserTicket} from "../model/user-ticket";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserTicketService {

  userTickets: UserTicket[] = [];

  constructor(private readonly http: HttpClient) {}

  getHistoryTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${environment.apiUrl}tickets/ticketshistory`);
  }

  getActiveTickets(): Observable<UserTicket[]>{
    return this.http.get<UserTicket[]>(`${environment.apiUrl}tickets/mytickets`);
  }

  addNewUserTickets(ticketId: number, discountId: number | undefined, quantity: number):Observable<UserTicket>{
    return this.http.post<UserTicket>(`${environment.apiUrl}tickets/buy`,
      {
        'ticketId': ticketId,
        'discountId': discountId,
        'quantity': quantity
      });
  }

  isTicketValid(ticketCode: string, vehicleId: string): Observable<any>{
    return this.http.post<any>(`${environment.apiUrl}validation/check`,
      {
        'ticketCode': ticketCode,
        'vehicleId': vehicleId
      });
  }

}
