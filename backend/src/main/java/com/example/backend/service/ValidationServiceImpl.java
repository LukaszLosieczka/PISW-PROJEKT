package com.example.backend.service;

import com.example.backend.dto.TicketValidation;
import com.example.backend.dto.ValidationDto;
import com.example.backend.dto.mapper.ValidationMapper;
import com.example.backend.model.UserTicket;
import com.example.backend.model.Validation;
import com.example.backend.model.enums.TicketType;
import com.example.backend.repository.UserTicketRepository;
import com.example.backend.repository.ValidationRepository;
import com.example.backend.service.ticketvalidator.TicketValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationServiceImpl implements ValidationService{

    private final ValidationRepository validationRepository;
    private final UserTicketRepository userTicketRepository;
    private final ValidationMapper validationMapper;
    private final List<TicketValidator> ticketValidators;

    @Override
    public ValidationDto validateTicket(TicketValidation validation) {
        UserTicket userTicket = userTicketRepository.findByCode(validation.getTicketCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket with code " + validation.getTicketCode() + " not found!"));
        if(userTicket.getTicket().getTicketType().equals(TicketType.SEASON)){
            throw new IllegalArgumentException("Season tickets cannot be validated!");
        }
        if(userTicket.getValidation() != null){
            throw new IllegalArgumentException("Ticket with code " + userTicket.getCode() + " was already validated!");
        }
        Validation newValidation = Validation.builder()
                .validationTime(LocalDateTime.now())
                .vehicleId(validation.getVehicleId())
                .userTicket(userTicket)
                .build();
        validationRepository.save(newValidation);
        return validationMapper.toDto(newValidation);
    }

    @Override
    public Boolean isTicketValid(TicketValidation validation) {
        UserTicket ticket = userTicketRepository.findByCode(validation.getTicketCode())
                .orElseThrow(() -> new IllegalArgumentException("Ticket with code " + validation.getTicketCode() + " not found!"));
        TicketValidator ticketValidator = getTicketValidator(ticket.getTicket().getTicketType());
        return ticketValidator.isTicketValid(ticket, validation.getVehicleId());
    }

    @Override
    public Boolean isTicketActive(UserTicket ticket) {
        TicketValidator ticketValidator = getTicketValidator(ticket.getTicket().getTicketType());
        return ticketValidator.isTicketActive(ticket);
    }

    private TicketValidator getTicketValidator(TicketType ticketType){
        return ticketValidators.stream()
                .filter(ticketValidator -> ticketValidator.canHandle(ticketType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown ticket type: " + ticketType.toString()));
    }
}
