package com.example.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "discount_percent", nullable = false, precision = 19, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "name", nullable = false)
    private String name;

}