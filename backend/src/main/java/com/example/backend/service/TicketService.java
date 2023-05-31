package com.example.backend.service;

import com.example.backend.dto.BuyTicket;
import com.example.backend.dto.UserTicketDto;

import java.util.List;

public interface TicketService {

    List<UserTicketDto> buyTicket(BuyTicket ticket, String userLogin);

    List<UserTicketDto> getUserTickets(String userLogin, Boolean active);
}
