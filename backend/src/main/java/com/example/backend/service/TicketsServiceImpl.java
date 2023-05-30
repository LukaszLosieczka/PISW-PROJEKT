package com.example.backend.service;

import com.example.backend.model.Ticket;
import com.example.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketsServiceImpl implements TicketsService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAvailableTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> getAvailableTicket(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

}
