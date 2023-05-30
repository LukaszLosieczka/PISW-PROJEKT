package com.example.backend.controller;

import com.example.backend.model.Ticket;
import com.example.backend.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tickets")
public class TicketsController {

    @Autowired
    TicketsService ticketsService;

    @GetMapping(path = "/available_tickets")
    ResponseEntity<Object> getAvailableTickets() {
        List<Ticket> availableTickets = ticketsService.getAvailableTickets();

        if (!availableTickets.isEmpty()) {
            return new ResponseEntity<>(availableTickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Brak dostępnych biletów", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/available_tickets/{id}")
    ResponseEntity<Object> getAvailableTicket(@PathVariable Long id) {
        Optional<Ticket> availableTicket = ticketsService.getAvailableTicket(id);

        if (!availableTicket.isEmpty()) {
            return new ResponseEntity<>(availableTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Brak dostępnego biletu o podanym id", HttpStatus.BAD_REQUEST);
        }
    }

}
