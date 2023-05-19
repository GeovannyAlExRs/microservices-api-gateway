package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import com.company.dtk.microservicesapigateway.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    // TODO: return USER token (LOGIN)
    @Override
    public Users singInAndReturnJWT(Users singInRequest) {

        // Instance authentication of username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(singInRequest.getUsername(), singInRequest.getPassword())
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
