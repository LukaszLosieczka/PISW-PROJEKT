package com.example.backend.controller;

import com.example.backend.IntegrationTestBase;
import com.example.backend.model.*;
import com.example.backend.model.enums.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    @WithMockUser(username = "janek123", roles = {"TICKET_COLLECTOR"})
    void shouldReturnThatTicketIsValid() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_TICKET_COLLECTOR").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        UserTicket userTicket = existingUserTicket(userTicket(ticket, discount, user));
        String vehicleId = UUID.randomUUID().toString();
        existingValidation(validation(userTicket, vehicleId));
        String creationRequest = """
                                 {
                                    "ticketCode": "%s",
                                    "vehicleId": "%s"
                                 }
                                 """.formatted(userTicket.getCode(), vehicleId);

        // when then
        mockMvc.perform(post("/tickets/validation").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", is(true)))
                .andExpect(jsonPath("$.message").isNotEmpty());
        List<UserTicket> result = getAllUserTickets();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUser().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    @WithMockUser(username = "janek123", roles = {"TICKET_COLLECTOR"})
    void shouldReturnThatTicketIsInvalidWhenValidationNotFound() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_TICKET_COLLECTOR").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        UserTicket userTicket = existingUserTicket(userTicket(ticket, discount, user));
        String vehicleId = UUID.randomUUID().toString();
        String creationRequest = """
                                 {
                                    "ticketCode": "%s",
                                    "vehicleId": "%s"
                                 }
                                 """.formatted(userTicket.getCode(), vehicleId);

        // when then
        mockMvc.perform(post("/tickets/validation").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", is(false)))
                .andExpect(jsonPath("$.message").isNotEmpty());
        List<UserTicket> result = getAllUserTickets();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUser().getLogin()).isEqualTo(user.getLogin());
    }


    private User userWithRole(String login, Role role){
       return User.builder()
                .name("kowalski")
                .surname("jan")
                .login(login)
                .password("$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK")//123456
                .role(role)
                .build();
    }

    private Ticket ticket(){
        return Ticket.builder()
                .ticketType(TicketType.SINGLE)
                .description("wszystkie linie")
                .price(BigDecimal.TEN)
                .validityPeriodSec(1000L)
                .name("jednorazowy")
                .build();
    }

    private Discount discount(){
        return Discount.builder()
                .name("studencka 50%")
                .discountPercent(BigDecimal.valueOf(50))
                .build();
    }

    private UserTicket userTicket(Ticket ticket, Discount discount, User user){
        return UserTicket.builder()
                .code(UUID.randomUUID().toString())
                .purchaseTime(LocalDateTime.now())
                .ticket(ticket)
                .discount(discount)
                .user(user)
                .build();
    }

    private Validation validation(UserTicket userTicket, String vehicleId){
        return Validation.builder()
                .validationTime(LocalDateTime.now())
                .vehicleId(vehicleId)
                .userTicket(userTicket)
                .build();
    }

}