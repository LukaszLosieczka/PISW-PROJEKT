package com.example.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketDto {
    private String code;
    private String ticketType;
    private String ticketPrice;
    private String discount;
    private LocalDateTime purchaseTime;
    private Boolean isValidated;
}
