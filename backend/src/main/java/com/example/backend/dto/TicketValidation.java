package com.example.backend.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidation {
    @NotEmpty()
    String ticketCode;

    @Size(min=8)
    @Size(max=8)
    @NotEmpty()
    String vehicleId;
}
