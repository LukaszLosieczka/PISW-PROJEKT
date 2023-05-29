package com.example.backend.controller;

import com.example.backend.model.Ticket;
import com.example.backend.service.TicketsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/tickets")
public class TicketsController {

    TicketsService ticketsService;

    @GetMapping(path = "/available_tickets")
    ResponseEntity<Object> getAvailableTickets() {
        List<Ticket> availableTickets= ticketsService.getAvailableTickets();
        return new ResponseEntity<>(availableTickets, HttpStatus.OK);
    }

}
