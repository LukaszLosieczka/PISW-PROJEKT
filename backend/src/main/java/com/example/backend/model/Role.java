package com.example.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @Column(nullable = false)
    private String name;

}