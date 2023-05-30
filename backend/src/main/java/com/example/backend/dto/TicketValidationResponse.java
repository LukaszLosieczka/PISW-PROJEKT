package com.example.backend.dto;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponse {
    Boolean isValid;
    String message;
}
