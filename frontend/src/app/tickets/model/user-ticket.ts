import {Ticket} from "./ticket";
import {Validation} from "./validation";

export interface UserTicket {
  code: number
  ticketType: string
  discount: string
  ticketPrice: string
  purchase_time: string
  isValidated: boolean
  ticket: Ticket
  validation: Validation
}
