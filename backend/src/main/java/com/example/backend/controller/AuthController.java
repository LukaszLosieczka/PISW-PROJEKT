package com.example.backend.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.backend.dto.AuthTokens;
import com.example.backend.dto.LoginUser;
import com.example.backend.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    ResponseEntity<Object> login(@Valid @RequestBody LoginUser loginUser)
            throws AuthenticationException, JsonProcessingException, IllegalArgumentException, JWTCreationException {
        AuthTokens tokens;
        try{
            tokens = service.authenticateUser(loginUser);
        }catch(BadCredentialsException e){
            return new ResponseEntity<>("Invalid login or password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @GetMapping("/refresh_token")
    ResponseEntity<Object> refreshToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                AuthTokens tokens = service.refreshTokens(refreshToken);
                return new ResponseEntity<>(tokens, HttpStatus.OK);
            } catch (Exception exception) {
                return new ResponseEntity<>(exception.getMessage(), FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("refresh token not provided", HttpStatus.BAD_REQUEST);
        }
    }
}
