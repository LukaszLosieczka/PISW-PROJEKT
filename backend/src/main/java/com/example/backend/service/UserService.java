package com.example.backend.service;

import com.example.backend.dto.RegisterUser;
import com.example.backend.model.User;

public interface UserService {
    User registerNewUserAccount(RegisterUser userDto);
}
