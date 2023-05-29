package com.example.backend.controller;

import com.example.backend.dto.RegisterUser;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterUser userDto) {
        try {
            userService.registerNewUserAccount(userDto);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }
}
