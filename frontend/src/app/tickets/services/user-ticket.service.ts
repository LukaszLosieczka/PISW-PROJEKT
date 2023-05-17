import {Injectable} from '@angular/core';
import {Ticket} from "../../tickets/model/ticket";
import {Discount} from "../../tickets/model/discount";
import {UserTicket} from "../../tickets/model/user-ticket";
import {TicketType} from "../../tickets/model/ticket-type";

@Injectable({
  providedIn: 'root'
})
export class UserTicketService {

  userTickets: UserTicket[] = [];
  userTicket: UserTicket;
  Ticket: Ticket;
  Discount: Discount | null;
  Quantity: number;

  constructor() {
    this.createTestSignleTicket();
    this.createTestTimeTicket();
  }

  createTestSignleTicket(): void {
    let testSingleTicket: UserTicket = {
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
        vehicle_id: 1234,
        validation_time: new Date().toLocaleString()
      },
      quantity: 1
    }

    this.userTickets.push(testSingleTicket);
  }

  createTestTimeTicket(): void {
    let testTimeTicket: UserTicket = {
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
        vehicle_id: 5678,
        validation_time: new Date().toLocaleString()
      },
      quantity: 1
    }

    this.userTickets.push(testTimeTicket);
  }

  addNewUserTickets(): void {
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

  checkUserTicketExists(userTicketCode: number): boolean {
    return this.userTickets.some(ticket => ticket.code === Number(userTicketCode));
  }

  checkUserTicketHasVehicle(userTicketCode: number, vehicle: number): boolean {
    const foundUserTicket: Array<UserTicket> = this.userTickets.filter(ticket => ticket.code === Number(userTicketCode));

    if (foundUserTicket && foundUserTicket[0].validation !== null) {
        return this.userTickets.filter(ticket => ticket.code === Number(userTicketCode) && ticket.validation?.vehicle_id === Number(vehicle)).length !== 0;
    }
    return false;
  }

}
