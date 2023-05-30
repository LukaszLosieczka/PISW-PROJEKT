package com.example.backend.service;

import com.example.backend.dto.AuthTokens;
import com.example.backend.dto.LoginUser;
import com.example.backend.exception.TokenExpiredException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    @Value("${jwt.token.access-token.expiration-time}")
    private int accessTokenExp;

    @Value("${jwt.token.refresh-token.expiration-time}")
    private int refreshTokenExp;

    @Override
    public AuthTokens authenticateUser(LoginUser loginUser) throws JsonProcessingException, BadCredentialsException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getLogin(), loginUser.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Password or login invalid");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String login = authentication.getName();
        User user = userRepository.findByLogin(login);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final String accessToken = tokenProvider.generateAccessToken(user, authorities, accessTokenExp);
        final String refreshToken = tokenProvider.generateRefreshToken(user, refreshTokenExp);
        return new AuthTokens(accessToken, refreshToken);
    }

    @Override
    public AuthTokens refreshTokens(String refreshToken) throws TokenExpiredException {
        String accessToken;
        try {
            String login = tokenProvider.getUsernameFromToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            User user = userRepository.findByLogin(login);
            accessToken = tokenProvider.generateAccessToken(user, userDetails.getAuthorities(), accessTokenExp);
        }catch (Exception e){
            throw new TokenExpiredException();
        }
        return new AuthTokens(accessToken, refreshToken);
    }
}
