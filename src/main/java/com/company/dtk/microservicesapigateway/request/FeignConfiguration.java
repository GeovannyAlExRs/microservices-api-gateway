package com.company.dtk.microservicesapigateway.request;

import com.company.dtk.microservicesapigateway.model.Users;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    //Authentication type Basic
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${service.security.secure-key-username}") String username,
            @Value("${service.security.secure-key-password}") String password) {

        return new BasicAuthRequestInterceptor(username, password);
    }
}
