package com.example.backend.service.ticketvalidator;

import com.example.backend.model.UserTicket;
import com.example.backend.model.enums.TicketType;

public interface TicketValidator {

    Boolean isTicketValid(UserTicket ticket, String vehicleId);

    Boolean isTicketActive(UserTicket ticket);

    Boolean canHandle(TicketType type);
}
