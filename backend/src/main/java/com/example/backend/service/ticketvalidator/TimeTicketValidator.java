package com.example.backend.service.ticketvalidator;

import com.example.backend.model.UserTicket;
import com.example.backend.model.enums.TicketType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeTicketValidator implements TicketValidator{
    @Override
    public Boolean isTicketValid(UserTicket ticket, String vehicleId) {
        if(ticket.getValidation() == null){
            return false;
        }
        LocalDateTime expirationDate = ticket.getValidation()
                .getValidationTime()
                .plusSeconds(ticket.getTicket().getValidityPeriodSec());
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @Override
    public Boolean isTicketActive(UserTicket ticket) {
        if(ticket.getValidation() == null){
            return true;
        }
        LocalDateTime expirationDate = ticket.getValidation()
                .getValidationTime()
                .plusSeconds(ticket.getTicket().getValidityPeriodSec());
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @Override
    public Boolean canHandle(TicketType type) {
        return type == TicketType.TIME;
    }
}
