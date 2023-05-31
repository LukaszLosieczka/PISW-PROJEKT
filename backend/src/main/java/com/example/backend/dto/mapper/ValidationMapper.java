package com.example.backend.dto.mapper;

import com.example.backend.dto.ValidationDto;
import com.example.backend.model.Validation;
import org.springframework.stereotype.Component;

@Component
public class ValidationMapper {

    public ValidationDto toDto(Validation validation){
        ValidationDto validationDto = new ValidationDto();
        validationDto.setValidationTime(validation.getValidationTime());
        validationDto.setVehicleId(validation.getVehicleId());
        validationDto.setTicketCode(validation.getUserTicket().getCode());
        return validationDto;
    }
}
