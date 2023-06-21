import {Ticket} from "./ticket";
import {Validation} from "./validation";
import {TicketType} from "./ticket-type";

export interface UserTicket {
  code: number
  ticketName: string
  ticketType: TicketType
  discount: string
  ticketPrice: string
  purchaseTime: string
  isValidated: boolean
  ticket: Ticket
  validation: Validation
}
