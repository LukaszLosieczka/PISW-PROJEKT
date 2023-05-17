import {Injectable} from '@angular/core';
import {Ticket} from "../../tickets/model/ticket";
import {Discount} from "../../tickets/model/discount";
import {UserTicket} from "../../tickets/model/user-ticket";
import {TicketType} from "../../tickets/model/ticket-type";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  Ticket: Ticket;
  Discount: Discount | null;
  Quantity: number;

  userTickets: UserTicket[] = [];
  userTicket: UserTicket;

  constructor() {
    let testTicket: UserTicket = {
      code: 100000,
      ticket: {
        id: 1,
        name: 'jednorazowy',
        description: 'wszystkie linie',
        price: 2.30,
        type: TicketType.Single,
        validityPeriod: 0
      },
      discount: 0,
      purchase_time: new Date().toLocaleString(),
      validation: {
        id: 1,
        ticket_code: 100000,
        vehicle_id: 123,
        validation_time: new Date().toLocaleString()
      },
      quantity: 1
    }
    this.userTickets.push(testTicket);

    let testTicket2: UserTicket = {
      code: 100001,
      ticket: {
        id: 2,
        name: '15-minutowy',
        description: 'wszystkie linie',
        price: 1.60,
        type: TicketType.Time,
        validityPeriod: 900
      },
      discount: 0,
      purchase_time: new Date().toLocaleString(),
      validation: {
        id: 2,
        ticket_code: 100001,
        vehicle_id: 124,
        validation_time: new Date().toLocaleString()
      },
      quantity: 1
    }
    this.userTickets.push(testTicket2);
  }

  addNewTickets(): void {
    for (let i = 0; i < this.Quantity; i++) {
      this.userTicket = {
        code: this.generateUserTicketCode(),
        ticket: this.Ticket,
        discount: this.Discount ? this.Discount.discountPercent : 0,
        validation: null,
        purchase_time: new Date().toLocaleString(),
        quantity: this.Quantity
      }
      this.userTickets.push(this.userTicket);
    }
  }

  generateUserTicketCode(): number {
    let min = 100000;
    let max = 999999;
    return Math.floor(Math.random() * (max - min) + min);
  }

  logIn(username:string, password: string): void{
    this.isLoggedIn = true;
  }

  logout(): void {
    this.isLoggedIn = false;
  }

  register(): void{

  }

  checkTicketExists(ticketCode: number): boolean {
    if (this.userTickets.length === 0) {
      return true;
    }
    return this.userTickets.some(ticket => ticket.code === Number(ticketCode));
  }

  checkTicketHasVehicle(ticketCode: number, vehicle: number): boolean {
    if (this.userTickets.length === 0) {
      return true;
    }
    const foundTicket: Array<UserTicket> = this.userTickets.filter(ticket => ticket.code === Number(ticketCode));
    if (foundTicket) {
      if (foundTicket[0].validation !== null) {
        return this.userTickets.filter(ticket => ticket.code === Number(ticketCode) && ticket.validation?.vehicle_id === Number(vehicle)).length !== 0;
      }
    }
    return false;
  }

}
