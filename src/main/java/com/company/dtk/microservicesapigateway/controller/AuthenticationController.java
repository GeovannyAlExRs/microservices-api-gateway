package com.company.dtk.microservicesapigateway.controller;

import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.service.AuthenticationService;
import com.company.dtk.microservicesapigateway.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsersService usersService;

    private final Log log = LogFactory.getLog(getClass());

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody Users users) {

        // Verify that the user is not registered
        if (usersService.findByUsername(users.getUsername()).isPresent()) {
            log.info("Ingreso al IF " + HttpStatus.CONFLICT);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(usersService.findByEmail(users.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        log.info("ENTRO POR AQUI " + HttpStatus.CREATED);
        return new ResponseEntity<>(usersService.createUser(users), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody Users users) {
        log.info("-> SIGN IN USER: " + users);
        return new ResponseEntity<>(
                authenticationService.singInAndReturnJWT(users), HttpStatus.OK
        );
    }
}
