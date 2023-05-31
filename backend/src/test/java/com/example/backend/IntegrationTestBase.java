package com.example.backend;

import com.example.backend.model.*;
import com.example.backend.model.enums.TicketType;
import com.example.backend.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        validationRepository.deleteAll();
        userTicketRepository.deleteAll();
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
    protected Ticket existingTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }
    protected Discount existingDiscount(Discount discount){
        return discountRepository.save(discount);
    }
    protected UserTicket existingUserTicket(UserTicket userTicket){
        return userTicketRepository.save(userTicket);
    }
    protected List<UserTicket> getAllUserTickets(){
        return userTicketRepository.findAll();
    }
    protected Validation existingValidation(Validation validation){
        return validationRepository.save(validation);
    }

    protected List<Validation> getAllValidations(){
        return validationRepository.findAll();
    }

    protected User userWithRole(String login, Role role){
        return User.builder()
                .name("kowalski")
                .surname("jan")
                .login(login)
                .password("$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK")//123456
                .role(role)
                .build();
    }

    protected Ticket ticket(){
        return Ticket.builder()
                .ticketType(TicketType.SINGLE)
                .description("wszystkie linie")
                .price(BigDecimal.TEN)
                .validityPeriodSec(1000L)
                .name("jednorazowy")
                .build();
    }

    protected  Discount discount(){
        return Discount.builder()
                .name("studencka 50%")
                .discountPercent(BigDecimal.valueOf(50))
                .build();
    }

    protected  UserTicket userTicket(Ticket ticket, Discount discount, User user){
        return UserTicket.builder()
                .code(UUID.randomUUID().toString())
                .purchaseTime(LocalDateTime.now())
                .ticket(ticket)
                .discount(discount)
                .user(user)
                .build();
    }
    protected  UserTicket userTicket(Ticket ticket, Discount discount, User user, LocalDateTime purchaseTime){
        return UserTicket.builder()
                .code(UUID.randomUUID().toString())
                .purchaseTime(purchaseTime)
                .ticket(ticket)
                .discount(discount)
                .user(user)
                .build();
    }

    protected  Validation validation(UserTicket userTicket, String vehicleId){
        return Validation.builder()
                .validationTime(LocalDateTime.now())
                .vehicleId(vehicleId)
                .userTicket(userTicket)
                .build();
    }

    protected  Validation validation(UserTicket userTicket, String vehicleId, LocalDateTime validationTime){
        return Validation.builder()
                .validationTime(validationTime)
                .vehicleId(vehicleId)
                .userTicket(userTicket)
                .build();
    }

}
