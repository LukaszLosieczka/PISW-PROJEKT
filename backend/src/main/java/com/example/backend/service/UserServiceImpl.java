package com.example.backend.service;

import com.example.backend.dto.RegisterUser;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else{
            log.info("User found in the database: {}", username);
        }
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                true,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
    }

    @Override
    public User registerNewUserAccount(RegisterUser userDto) {
        if(userRepository.findByLogin(userDto.getLogin()) != null){
            throw new IllegalArgumentException("Użytkownik o loginie: " + userDto.getLogin() + " już istnieje");
        }
        Optional<Role> role = roleRepository.findById("ROLE_USER");
        if(role.isEmpty()){
            throw new RuntimeException("User role not exists");
        }
        User user = new User();
        user.setRole(role.get());
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return user;
    }
}
