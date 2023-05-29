package com.example.backend.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotEmpty()
    @Size(min = 3, message = "Imie musi mieć przynajmniej 3 znaki")
    private String surname;

    @NotEmpty()
    @Size(min = 3, message = "Nazwisko musi mieć przynajmniej 3 znaki")
    private String name;

    @NotEmpty()
    @Size(min = 3, message = "Login musi mieć przynajmniej 3 znaki")
    private String login;

    @NotEmpty()
    @Size(min = 6, max = 32, message = "Hasło musi zawierać od 6 do 32 znaków")
    private String password;
}