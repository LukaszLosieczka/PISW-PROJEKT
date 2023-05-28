package com.example.backend.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
