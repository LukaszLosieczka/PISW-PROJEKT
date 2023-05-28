package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "validation")
    private UserTicket userTicket;

    @Column(name = "validation_t_ime", nullable = false)
    private LocalDateTime validationTIme;

    @Column(name = "vehicle_id", nullable = false)
    private String vehicle_id;

}