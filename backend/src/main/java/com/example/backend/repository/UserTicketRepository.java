package com.example.backend.repository;

import com.example.backend.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    Optional<UserTicket> findByCode(String ticketCode);
}