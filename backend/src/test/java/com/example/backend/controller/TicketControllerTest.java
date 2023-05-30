package com.example.backend.controller;

import com.example.backend.model.Ticket;
import com.example.backend.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketsService;

    private final String USERNAME = "admin1";
    private final String PASSWORD = "$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK";
    private final String ROLE = "USER";

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableTickets() throws Exception {
        Ticket ticket1 = Ticket.builder()
                .name("Ticket 1")
                .build();
        Ticket ticket2 = Ticket.builder()
                .name("Ticket 2")
                .build();
        List<Ticket> mockTickets = Arrays.asList(ticket1, ticket2);

        when(ticketsService.getAvailableTickets()).thenReturn(mockTickets);

        mockMvc.perform(get("/tickets/available_tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.length()").value(2))
                        .andExpect(jsonPath("$[0].name").value("Ticket 1"))
                        .andExpect(jsonPath("$[1].name").value("Ticket 2"));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableTicketsEmpty() throws Exception {
        List<Ticket> mockTicketsEmpty = new ArrayList<>();

        when(ticketsService.getAvailableTickets()).thenReturn(mockTicketsEmpty);

        mockMvc.perform(get("/tickets/available_tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Brak dostępnych biletów"));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableTicketExists() throws Exception {
        Long ticketId = 1L;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);

        when(ticketsService.getAvailableTicket(ticketId)).thenReturn(Optional.of(mockTicket));

        mockMvc.perform(get("/tickets/available_tickets/{id}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(ticketId));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableTicketNotExists() throws Exception {
        Long ticketId = 1L;

        when(ticketsService.getAvailableTicket(ticketId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tickets/available_tickets/{id}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Brak dostępnego biletu o podanym id"));
    }

}
