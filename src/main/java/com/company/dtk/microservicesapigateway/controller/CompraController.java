package com.company.dtk.microservicesapigateway.controller;

import com.company.dtk.microservicesapigateway.request.CompraServiceRequest;
import com.company.dtk.microservicesapigateway.security.UsersPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/compra")
public class CompraController {

    @Autowired
    private CompraServiceRequest compraServiceRequest;

    @PostMapping
    public ResponseEntity<?> createCompra(@RequestBody Object compra) {
        return new ResponseEntity<>(
                compraServiceRequest.createCompra(compra),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCompraOfUser(@AuthenticationPrincipal UsersPrincipal usersPrincipal) {
        return ResponseEntity.ok(compraServiceRequest.getAllCompraOfUser(usersPrincipal.getId()));
    }
}
