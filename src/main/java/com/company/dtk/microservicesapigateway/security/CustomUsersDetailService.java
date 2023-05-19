package com.company.dtk.microservicesapigateway.security;

import com.company.dtk.microservicesapigateway.model.Users;
import com.company.dtk.microservicesapigateway.service.UsersService;
import com.company.dtk.microservicesapigateway.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUsersDetailService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Allows you to find a user who is registered in the DB
        Users users = usersService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe: " + username));

        // TODO: Convert the ROLE that is in my DB to the Spring Security context format as an Authority
        Set<GrantedAuthority> authoritySet = Set.of(SecurityUtils.simpleGrantedAuthority(users.getRole().name()));

        return UsersPrincipal.builder()
                .users(users)
                .id(users.getId())
                .username(users.getUsername())
                .password(users.getPassword())
                .authorities(authoritySet)
                .build();
    }
}
