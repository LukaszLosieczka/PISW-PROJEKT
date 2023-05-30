package com.example.backend.controller;

import com.example.backend.dto.BuyTicket;
import com.example.backend.dto.UserTicketDto;
import com.example.backend.model.Ticket;
import com.example.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/buy")
    ResponseEntity<Object> buyTickets(@Valid @RequestBody BuyTicket buyTicket) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> tickets = ticketService.buyTicket(buyTicket, authentication.getName());
            return new ResponseEntity<>(tickets, HttpStatus.CREATED);
        } catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/mytickets")
    ResponseEntity<Object> getMyTickets() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> myTickets = ticketService.getUserTickets(authentication.getName(), true);
            return new ResponseEntity<>(myTickets, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/ticketshistory")
    ResponseEntity<Object> getTicketsHistory() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> myTickets = ticketService.getUserTickets(authentication.getName(), false);
            return new ResponseEntity<>(myTickets, HttpStatus.OK);
        } catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/available_tickets")
    ResponseEntity<Object> getAvailableTickets() {
        List<Ticket> availableTickets = ticketService.getAvailableTickets();

        if (!availableTickets.isEmpty()) {
            return new ResponseEntity<>(availableTickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Brak dostępnych biletów", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/available_tickets/{id}")
    ResponseEntity<Object> getAvailableTicket(@PathVariable Long id) {
        Optional<Ticket> availableTicket = ticketService.getAvailableTicket(id);

        if (!availableTicket.isEmpty()) {
            return new ResponseEntity<>(availableTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Brak dostępnego biletu o podanym id", HttpStatus.BAD_REQUEST);
        }
    }

}
