package com.example.backend.dto;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokens {
    private String access_token;
    private String refresh_token;
}