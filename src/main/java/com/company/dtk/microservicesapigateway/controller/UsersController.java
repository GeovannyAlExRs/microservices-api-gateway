package com.company.dtk.microservicesapigateway.controller;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import com.company.dtk.microservicesapigateway.service.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private final Log log = LogFactory.getLog(getClass());

    // Update user role (security token)
    @PutMapping("update/{role}")
    public ResponseEntity<?> updateRoleUsers(@AuthenticationPrincipal UsersPrincipal usersPrincipal, @PathVariable Roles role) {

        // UserPrincipal: maps to the user who logged in thanks to the valid token
        log.info("usersPrincipal = " + usersPrincipal.getUsername() + "update/{role} = " + role);
        usersService.updateUserRole(usersPrincipal.getUsername(), role);

        return ResponseEntity.ok(true);

    }

    // Get Token Session current
    @GetMapping()
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UsersPrincipal usersPrincipal) {
        return new ResponseEntity<>(
                usersService.findByUsernameGetToken(usersPrincipal.getUsername()),
                HttpStatus.OK
                );
    }
}
