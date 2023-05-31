package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.model.Users;

import java.util.Optional;

public interface UsersService {
    Users createUser(Users users);

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    void updateUserRole(String username, Roles role);

    Users findByUsernameGetToken(String username);
}
