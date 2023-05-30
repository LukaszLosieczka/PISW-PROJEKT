package com.example.backend.controller;

import com.example.backend.model.Discount;
import com.example.backend.model.Ticket;
import com.example.backend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping(path = "/available_discounts")
    ResponseEntity<Object> getAvailableDiscounts() {
        List<Discount> availableDiscounts = discountService.getAvailableDiscounts();

        if (!availableDiscounts.isEmpty()) {
            return new ResponseEntity<>(availableDiscounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Brak dostępnych zniżek", HttpStatus.BAD_REQUEST);
        }
    }

}
