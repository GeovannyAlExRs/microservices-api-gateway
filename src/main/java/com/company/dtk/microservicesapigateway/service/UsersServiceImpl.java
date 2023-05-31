package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.repository.UsersRepository;
import com.company.dtk.microservicesapigateway.security.jwt.JwtProviderImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsersServiceImpl implements  UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProviderImpl jwtProvider;

    @Override
    public Users createUser(Users users) {

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Roles.USER);
        users.setDate(LocalDateTime.now());

        Users userToken = usersRepository.save(users);
        userToken.setToken(jwtProvider.generateToken(userToken));

        return userToken;
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void updateUserRole(String username, Roles role) {
        usersRepository.updateUserRole(username, role);
    }

    @Override
    public Users findByUsernameGetToken(String username) {
        Users users = usersRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("El usuario " + username + " no existe")
        );

        String jwtToken = jwtProvider.generateToken(users);
        users.setToken(jwtToken);
        return users;
    }
}
