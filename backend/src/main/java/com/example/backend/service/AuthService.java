package com.example.backend.service;

import com.example.backend.dto.AuthTokens;
import com.example.backend.dto.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {
    AuthTokens authenticateUser(LoginUser loginUser) throws JsonProcessingException, BadCredentialsException;
    AuthTokens refreshTokens(String refreshToken) throws JsonProcessingException;
}