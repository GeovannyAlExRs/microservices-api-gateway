package com.company.dtk.microservicesapigateway.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

public class SecurityUtils {

    public static final  String ROLE_PREFIX = "ROLE_";
    public static final  String AUTH_HEADER = "authorization";
    public static final  String AUTH_TOKEN_TYPE = "Bearer";
    public static final  String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE + " ";

    // TODO: Performs ROLE Conversion to Spring Security format as an Authority
    public static SimpleGrantedAuthority simpleGrantedAuthority(String role) {

        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role;

        return new SimpleGrantedAuthority(formattedRole);
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTH_HEADER);

        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
