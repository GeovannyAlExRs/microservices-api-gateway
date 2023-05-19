package com.company.dtk.microservicesapigateway.security.jwt;

import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import com.company.dtk.microservicesapigateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    // TODO: Function for create Token with structure Users of Spring Security
    @Override
    public String generateToken(UsersPrincipal auth) {
        // Get all authorities of roles Users
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Decrypt the token
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        // Return the Token Generated
        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Method to Authentication with JWT
    @Override
    public Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        Claims claims = extractClaims(httpServletRequest);
        if (claims == null) {
            return null;
        }
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays
                .stream(claims.get("roles").toString().split(","))
                        .map(SecurityUtils::simpleGrantedAuthority)
                        .collect(Collectors.toSet());

        // Create Object Users of Structure Spring Security
        UserDetails userDetails = UsersPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    // Method to check Token is valid
    @Override
    public boolean isTokenValid(HttpServletRequest httpServletRequest) {
        Claims claims = extractClaims(httpServletRequest);
        if (claims == null) {
            return false;
        }

        if (claims.getExpiration().before(new Date())) {
            return  false;
        }

        return true;
    }

    // Method to extract all CLAIMS (Claims: internal properties of TOKEN)
    private Claims extractClaims(HttpServletRequest httpServletRequest) {
        // Get request Token
        String token = SecurityUtils.extractAuthTokenFromRequest(httpServletRequest);

        if (token == null) {
            return null;
        }

        // Decrypt the access key
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }
}
