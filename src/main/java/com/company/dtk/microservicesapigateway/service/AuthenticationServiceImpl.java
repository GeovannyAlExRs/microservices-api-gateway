package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.repository.UsersRepository;
import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import com.company.dtk.microservicesapigateway.security.jwt.JwtProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsersRepository usersRepository;

    private final Log log = LogFactory.getLog(getClass());

    // TODO: return USER token (LOGIN)
    @Override
    public Users singInAndReturnJWT(Users singInRequest) {

        log.info("-> singInAndReturnJWT USER: " + singInRequest);

        // Find User by Email
        Users users = usersRepository.findByEmail(singInRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("El usuario " + singInRequest.getEmail() + " no existe")
        );

        // Instance authentication of username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), singInRequest.getPassword())
        );

        // Get authentication del Users (Structure Spring Security)
        UsersPrincipal usersPrincipal = (UsersPrincipal) authentication.getPrincipal();

        // Get Token Generated
        String jwt = jwtProvider.generateToken(usersPrincipal);

        Users singInUser = usersPrincipal.getUsers();
        singInUser.setToken(jwt); // save token of Users

        return singInUser;
    }
}
