package com.company.dtk.microservicesapigateway.security;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUsersDetailService customUsersDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        authenticationManagerBuilder.userDetailsService(customUsersDetailService).passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
                .cors().and().csrf().disable()
                .authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/api/authentication/sign-in", "/api/authentication/sign-up")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/gateway/inmueble").permitAll()
                .requestMatchers("/gateway/inmueble/**").hasRole(Roles.ADMIN.name())
                .anyRequest()
                .authenticated();

        return httpSecurity
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) //invoke Authorization Filter
                .build();

        /*return httpSecurity
                .securityMatcher("/api/authentication/sign-in", "/api/authentication/sign-up")
                .authorizeHttpRequests() // .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().cors()
                .and().csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) //invoke Authorization Filter
                .build();*/
    }

    // Start injection Authorization Filter
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    // Config CORS: to access to all endPoints from any origin
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
