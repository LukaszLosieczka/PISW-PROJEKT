package com.example.backend.service;

import com.example.backend.dto.BuyTicket;
import com.example.backend.dto.UserTicketDto;
import com.example.backend.dto.mapper.UserTicketMapper;
import com.example.backend.exception.DiscountNotFoundException;
import com.example.backend.exception.TicketNotFoundException;
import com.example.backend.model.Discount;
import com.example.backend.model.Ticket;
import com.example.backend.model.User;
import com.example.backend.model.UserTicket;
import com.example.backend.model.enums.TicketType;
import com.example.backend.repository.DiscountRepository;
import com.example.backend.repository.TicketRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserTicketRepository;
import com.example.backend.service.ticketvalidator.TicketValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService{

    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserTicketMapper userTicketMapper;

    private final List<TicketValidator> ticketValidators;

    @Override
    public List<UserTicketDto> buyTicket(BuyTicket buyTicket, String userLogin) {
        User user = userRepository.getByLogin(userLogin)
                .orElseThrow(() -> new IllegalArgumentException(userLogin + " not found"));;
        Ticket ticket = ticketRepository.findById(buyTicket.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket with given id not found"));
        Discount discount = null;
        if(buyTicket.getDiscountId() != null) {
            discount = discountRepository.findById(buyTicket.getDiscountId())
                    .orElseThrow(() -> new DiscountNotFoundException("Discount with given id not found"));
        }
        if(buyTicket.getQuantity() < 1){
            throw new IllegalArgumentException("Incorrect quantity of tickets");
        }
        List<UserTicket> userTicketsToSave = new ArrayList<>();
        for(int i=0; i < buyTicket.getQuantity(); i++){
            userTicketsToSave.add(createUserTicket(user, ticket, discount));
        }
        List<UserTicket> savedTickets = userTicketRepository.saveAll(userTicketsToSave);
        return userTicketMapper.toDtos(savedTickets);
    }

    @Override
    public List<UserTicketDto> getUserTickets(String userLogin, Boolean active) {
        User user = userRepository.getByLogin(userLogin)
                .orElseThrow(() -> new IllegalArgumentException(userLogin + " not found"));
        return user.getUserTickets().stream()
                .filter(userTicket -> getTicketValidator(userTicket.getTicket().getTicketType())
                        .isTicketActive(userTicket) == active)
                .map(userTicketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isTicketValid(String ticketCode, String vehicleId) {
        UserTicket ticket = userTicketRepository.findByCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Ticket with code " + ticketCode + " not found!"));
        TicketValidator ticketValidator = getTicketValidator(ticket.getTicket().getTicketType());
        return ticketValidator.isTicketValid(ticket, vehicleId);
    }

    private TicketValidator getTicketValidator(TicketType ticketType){
        return ticketValidators.stream()
                .filter(ticketValidator -> ticketValidator.canHandle(ticketType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown ticket type: " + ticketType.toString()));
    }

    private UserTicket createUserTicket(User user, Ticket ticket, Discount discount){
        return UserTicket.builder()
                .code(UUID.randomUUID().toString())
                .ticket(ticket)
                .user(user)
                .discount(discount)
                .purchaseTime(LocalDateTime.now())
                .build();
    }
}
