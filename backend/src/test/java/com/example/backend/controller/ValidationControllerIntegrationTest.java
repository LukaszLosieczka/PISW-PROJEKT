package com.example.backend.controller;

import com.example.backend.IntegrationTestBase;
import com.example.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ValidationControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldValidateTicketAndReturnValidation() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(userWithRole("janek123", role));
        Ticket ticket = existingTicket(ticket());
        Discount discount = existingDiscount(discount());
        UserTicket userTicket = existingUserTicket(userTicket(ticket, discount, user));
        String vehicleId = "12345678";
        String creationRequest = """
                                 {
                                    "ticketCode": "%s",
                                    "vehicleId": "%s"
                                 }
                                 """.formatted(userTicket.getCode(), vehicleId);

        // when then
        mockMvc.perform(post("/validation").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isCreated());
        List<Validation> result = getAllValidations();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getVehicleId()).isEqualTo(vehicleId);
        assertThat(result.get(0).getUserTicket().getCode()).isEqualTo(userTicket.getCode());
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
        String vehicleId = "12345678";
        existingValidation(validation(userTicket, vehicleId));
        String creationRequest = """
                                 {
                                    "ticketCode": "%s",
                                    "vehicleId": "%s"
                                 }
                                 """.formatted(userTicket.getCode(), vehicleId);

        // when then
        mockMvc.perform(post("/validation/check").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
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
        String vehicleId = "12345678";
        String creationRequest = """
                                 {
                                    "ticketCode": "%s",
                                    "vehicleId": "%s"
                                 }
                                 """.formatted(userTicket.getCode(), vehicleId);

        // when then
        mockMvc.perform(post("/validation/check").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", is(false)))
                .andExpect(jsonPath("$.message").isNotEmpty());
        List<UserTicket> result = getAllUserTickets();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUser().getLogin()).isEqualTo(user.getLogin());
    }
}