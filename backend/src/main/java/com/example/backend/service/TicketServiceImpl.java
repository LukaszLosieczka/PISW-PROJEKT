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
import com.example.backend.repository.DiscountRepository;
import com.example.backend.repository.TicketRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService{

    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserTicketMapper userTicketMapper;

    @Override
    public List<UserTicketDto> buyTicket(BuyTicket buyTicket) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(authentication.getName());
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
