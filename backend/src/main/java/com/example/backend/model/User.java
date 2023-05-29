package com.example.backend.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<UserTicket> userTickets = new ArrayList<>();

}