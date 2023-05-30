package com.example.backend.service;

import com.example.backend.model.Ticket;
import com.example.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketsServiceImpl implements TicketsService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAvailableTickets() {
        return ticketRepository.findAll();
    }

}
