package com.company.dtk.microservicesapigateway.security.jwt;

import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(UsersPrincipal auth);

    Authentication getAuthentication(HttpServletRequest httpServletRequest);

    boolean isTokenValid(HttpServletRequest httpServletRequest);
}
