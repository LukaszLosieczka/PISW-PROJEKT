package com.example.backend.service;

import com.example.backend.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketsService {

    List<Ticket>  getAvailableTickets();

    Optional<Ticket> getAvailableTicket(Long ticketId);
}
