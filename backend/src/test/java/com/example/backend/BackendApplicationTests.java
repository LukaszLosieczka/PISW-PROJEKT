package com.example.backend;

import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void contextLoads() {
        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);

        User user = new User();
        user.setLogin("admin1");
        user.setPassword("admin123");
        user.setName("admin");
        user.setSurname("admin");
        user.setRole(role);

        userRepository.save(user);

        userRepository.findAll();
    }

}
