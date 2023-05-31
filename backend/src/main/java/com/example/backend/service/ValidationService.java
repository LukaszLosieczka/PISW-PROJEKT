package com.example.backend.service;

import com.example.backend.dto.TicketValidation;
import com.example.backend.dto.ValidationDto;
import com.example.backend.model.UserTicket;

public interface ValidationService {

    ValidationDto validateTicket(TicketValidation validation);
    Boolean isTicketValid(TicketValidation validation);

    Boolean isTicketActive(UserTicket userTicket);
}
