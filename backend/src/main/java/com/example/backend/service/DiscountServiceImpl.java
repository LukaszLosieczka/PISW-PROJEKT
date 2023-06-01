package com.example.backend.service;

import com.example.backend.model.Discount;
import com.example.backend.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public List<Discount> getAvailableDiscounts() {
        return discountRepository.findAll();
    }

}
