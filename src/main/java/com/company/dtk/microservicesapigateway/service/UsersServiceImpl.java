package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.repository.UsersRepository;
import com.company.dtk.microservicesapigateway.security.jwt.JwtProviderImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    @Override
    public void updateUserRole(String username, Roles role) {
        usersRepository.updateUserRole(username, role);
    }
}
