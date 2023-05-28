package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @Column(name = "code", nullable = false)
    private Long code;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_login", nullable = false)
    private User user;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "validation_id")
    private Validation validation;

    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;
}