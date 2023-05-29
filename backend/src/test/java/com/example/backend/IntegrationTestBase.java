package com.example.backend;

import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = BackendApplication.class)
public abstract class IntegrationTestBase {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private UserTicketRepository userTicketRepository;
    @Autowired
    private ValidationRepository validationRepository;

    @AfterEach
    void cleanUpDB(){
        userTicketRepository.deleteAll();
        validationRepository.deleteAll();
        ticketRepository.deleteAll();
        discountRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    protected User existingUser(User user){
        return userRepository.save(user);
    }

    protected List<User> getAllUsers(){
        return userRepository.findAll();
    }

    protected Role existingRole(Role role){
        return roleRepository.save(role);
    }

    protected List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
