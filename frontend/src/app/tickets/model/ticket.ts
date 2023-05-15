import {TicketType} from "./ticket-type";

export interface Ticket {
  id: number
  name: string
  description: string
  price: number
  type: TicketType
  validityPeriod: number
}
