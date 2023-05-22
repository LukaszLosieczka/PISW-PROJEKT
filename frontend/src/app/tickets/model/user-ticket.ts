import {Ticket} from "./ticket";
import {Validation} from "./validation";
export interface UserTicket {
  code: number
  ticket: Ticket
  discount: number
  validation: Validation | null
  purchase_time: string
  quantity: number
}
