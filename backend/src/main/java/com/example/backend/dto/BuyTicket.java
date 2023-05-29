package com.example.backend.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicket {
    @NotNull
    private Long ticketId;
    private Long discountId;
    @NotNull
    private Integer quantity;
}
