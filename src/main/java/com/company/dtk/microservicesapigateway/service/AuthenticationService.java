package com.company.dtk.microservicesapigateway.service;

import com.company.dtk.microservicesapigateway.model.Users;

public interface AuthenticationService {
    Users singInAndReturnJWT(Users singInRequest);
}
