package com.example.backend.service;

import com.example.backend.model.Discount;

import java.util.List;

public interface DiscountService {

    List<Discount> getAvailableDiscounts();

}
