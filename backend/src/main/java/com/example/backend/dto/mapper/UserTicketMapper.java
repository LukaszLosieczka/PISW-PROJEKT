package com.example.backend.dto.mapper;

import com.example.backend.dto.UserTicketDto;
import com.example.backend.model.Discount;
import com.example.backend.model.Ticket;
import com.example.backend.model.UserTicket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTicketMapper {

    public UserTicketDto toDto(UserTicket userTicket){
        UserTicketDto userTicketDto = new UserTicketDto();
        userTicketDto.setCode(userTicket.getCode());
        userTicketDto.setTicketName(userTicket.getTicket().getName());
        userTicketDto.setTicketType(userTicket.getTicket().getTicketType());
        userTicketDto.setTicketPrice(calculatePrice(userTicket.getTicket(), userTicket.getDiscount()) + "zł");
        userTicketDto.setDiscount(getPercent(userTicket.getDiscount()));
        userTicketDto.setPurchaseTime(userTicket.getPurchaseTime());
        userTicketDto.setIsValidated(userTicket.getValidation() != null);
        return userTicketDto;
    }

    public List<UserTicketDto> toDtos(List<UserTicket> userTickets){
        return userTickets.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Double calculatePrice(Ticket ticket, Discount discount){
        double ticketPrice = ticket.getPrice().doubleValue();
        double discountPercent = discount != null ? discount.getDiscountPercent().doubleValue() : 0;
        return Math.round((ticketPrice - (ticketPrice * (discountPercent/100)))*100)/100.0;
    }

    private String getPercent(Discount discount){
        double value = discount != null ? discount.getDiscountPercent().doubleValue() : BigDecimal.ZERO.doubleValue();
        return value + "%";
    }
}
