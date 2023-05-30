package com.example.backend.service;

import com.example.backend.model.Ticket;

import java.util.List;

public interface TicketsService {

    List<Ticket>  getAvailableTickets();

}
