package com.example.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDto {
    private String ticketCode;
    private String vehicleId;
    private LocalDateTime validationTime;
}
