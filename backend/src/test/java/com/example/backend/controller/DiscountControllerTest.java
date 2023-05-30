package com.example.backend.controller;

import com.example.backend.model.Discount;
import com.example.backend.model.Ticket;
import com.example.backend.service.DiscountService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountService discountService;

    private final String USERNAME = "admin1";
    private final String PASSWORD = "$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK";
    private final String ROLE = "USER";

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableDiscounts() throws Exception {
        Discount discount1 = Discount.builder()
                .name("Discount 1")
                .build();
        Discount discount2 = Discount.builder()
                .name("Discount 2")
                .build();
        List<Discount> mockDiscounts = Arrays.asList(discount1, discount2);

        when(discountService.getAvailableDiscounts()).thenReturn(mockDiscounts);

        mockMvc.perform(get("/discounts/available_discounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Discount 1"))
                .andExpect(jsonPath("$[1].name").value("Discount 2"));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD, roles = ROLE)
    void testGetAvailableDiscountsEmpty() throws Exception {
        List<Discount> mockDiscountsEmpty = new ArrayList<>();

        when(discountService.getAvailableDiscounts()).thenReturn(mockDiscountsEmpty);

        mockMvc.perform(get("/discounts/available_discounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Brak dostępnych zniżek"));
    }

}
