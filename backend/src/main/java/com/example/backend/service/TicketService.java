package com.example.backend.service;

import com.example.backend.dto.BuyTicket;
import com.example.backend.dto.UserTicketDto;
import com.example.backend.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<UserTicketDto> buyTicket(BuyTicket ticket, String userLogin);

    List<UserTicketDto> getUserTickets(String userLogin, Boolean active);

    List<Ticket>  getAvailableTickets();

    Optional<Ticket> getAvailableTicket(Long ticketId);

}
