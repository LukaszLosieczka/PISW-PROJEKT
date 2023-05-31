package com.example.backend.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidation {
    @NotEmpty()
    String ticketCode;
    @NotEmpty()
    String vehicleId;
}
