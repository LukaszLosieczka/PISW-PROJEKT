package com.example.backend.dto;

import com.example.backend.model.enums.TicketType;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketDto {
    private String code;
    private String ticketName;
    private TicketType ticketType;
    private String ticketPrice;
    private String discount;
    private LocalDateTime purchaseTime;
    private Boolean isValidated;
}
