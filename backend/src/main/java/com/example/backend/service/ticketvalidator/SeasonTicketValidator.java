package com.example.backend.service.ticketvalidator;

import com.example.backend.model.UserTicket;
import com.example.backend.model.enums.TicketType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SeasonTicketValidator implements TicketValidator{

    @Override
    public Boolean isTicketValid(UserTicket ticket, String vehicleId) {
        return isTicketActive(ticket);
    }

    @Override
    public Boolean isTicketActive(UserTicket ticket) {
        LocalDateTime expirationDate = ticket
                .getPurchaseTime()
                .plusSeconds(ticket.getTicket().getValidityPeriodSec());
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @Override
    public Boolean canHandle(TicketType type) {
        return type == TicketType.SEASON;
    }
}
