package com.example.backend.controller;

import com.example.backend.dto.TicketValidation;
import com.example.backend.dto.TicketValidationResponse;
import com.example.backend.dto.ValidationDto;
import com.example.backend.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/validation")
public class ValidationController {
    @Autowired
    private ValidationService validationService;

    @PostMapping()
    ResponseEntity<Object> validateTicket(@Valid @RequestBody TicketValidation ticketValidation) {
        try{
            ValidationDto validation = validationService.validateTicket(ticketValidation);
            return new ResponseEntity<>(validation, HttpStatus.CREATED);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_TICKET_COLLECTOR') or hasRole('ROLE_ADMIN')")
    @PostMapping("/check")
    ResponseEntity<Object> isTicketValid(@Valid @RequestBody TicketValidation ticketValidation) {
        try{
            Boolean isValid = validationService.isTicketValid(ticketValidation);
            TicketValidationResponse ticketValidationResponse = new TicketValidationResponse();
            ticketValidationResponse.setIsValid(isValid);
            if(isValid){
                ticketValidationResponse.setMessage("Bilet jest ważny!");
            }
            else{
                ticketValidationResponse.setMessage("Bilet jest nieważny!");
            }
            return new ResponseEntity<>(ticketValidationResponse, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
