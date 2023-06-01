package com.example.backend.controller;

import com.example.backend.IntegrationTestBase;
import com.example.backend.model.*;
import com.example.backend.model.enums.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.*;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


class TicketControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "janek123", roles = {"USER"})
    void shouldBuyOneTicketAndReturnIt() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        Integer quantity = 1;
        String creationRequest = """
                                 {
                                    "ticketId": "%d",
                                    "discountId": "%d",
                                    "quantity": "%d"
                                 }
                                 """.formatted(ticket.getId(), discount.getId(), quantity);

        // when then
        mockMvc.perform(post("/tickets/buy").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isCreated());
        List<UserTicket> result = getAllUserTickets();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUser().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    @WithMockUser(username = "janek123", roles = {"USER"})
    void shouldReturnListOfActiveTickets() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        UserTicket userTicket = existingUserTicket(userTicket(ticket, discount, user));

        // when then
        mockMvc.perform(get("/tickets/mytickets"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code", is(userTicket.getCode())));
    }

    @Test
    @WithMockUser(username = "janek123", roles = {"USER"})
    void shouldReturnListOfOldTickets() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        UserTicket userTicket = existingUserTicket(userTicket(ticket, discount, user));
        existingValidation(validation(userTicket, "12345678", LocalDateTime.now().minusDays(1)));

        // when then
        mockMvc.perform(get("/tickets/ticketshistory"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code", is(userTicket.getCode())));
    }

    @Test
    @WithMockUser(username = "janek123", roles = {"USER"})
    void shoukdReturnEmptyAvailableTicketsList() throws Exception {
        mockMvc.perform(get("/tickets/available_tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Brak dostępnych biletów"));
    }

    @Test
    @WithMockUser(username = "janek123", roles = {"USER"})
    void shouldNotReturnAvailableTicketByIdIfNotExists() throws Exception {
        Long ticketId = 1L;

        mockMvc.perform(get("/tickets/available_tickets/{id}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Brak dostępnego biletu o podanym id"));
    }

}