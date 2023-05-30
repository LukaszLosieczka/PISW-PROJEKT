package com.example.backend.controller;

import com.example.backend.dto.BuyTicket;
import com.example.backend.dto.TicketValidation;
import com.example.backend.dto.TicketValidationResponse;
import com.example.backend.dto.UserTicketDto;
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

@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/buy")
    ResponseEntity<Object> buyTickets(@Valid @RequestBody BuyTicket buyTicket) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> tickets = ticketService.buyTicket(buyTicket, authentication.getName());
            return new ResponseEntity<>(tickets, HttpStatus.CREATED);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/mytickets")
    ResponseEntity<Object> getMyTickets() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> myTickets = ticketService.getUserTickets(authentication.getName(), true);
            return new ResponseEntity<>(myTickets, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/ticketshistory")
    ResponseEntity<Object> getTicketsHistory() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<UserTicketDto> myTickets = ticketService.getUserTickets(authentication.getName(), false);
            return new ResponseEntity<>(myTickets, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_TICKET_COLLECTOR') or hasRole('ROLE_ADMIN')")
    @PostMapping("/validation")
    ResponseEntity<Object> isTicketValid(@Valid @RequestBody TicketValidation ticketValidation) {
        try{
            Boolean isValid = ticketService.isTicketValid(ticketValidation.getTicketCode(), ticketValidation.getVehicleId());
            TicketValidationResponse ticketValidationResponse = new TicketValidationResponse();
            ticketValidationResponse.setIsValid(isValid);
            if(isValid){
                ticketValidationResponse.setMessage("Ticket is still valid");
            }
            else{
                ticketValidationResponse.setMessage("Ticket is no longer valid!");
            }
            return new ResponseEntity<>(ticketValidationResponse, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
