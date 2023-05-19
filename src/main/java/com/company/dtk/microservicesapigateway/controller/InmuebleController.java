package com.company.dtk.microservicesapigateway.controller;

import com.company.dtk.microservicesapigateway.request.InmuebleServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/inmueble")
public class InmuebleController {

    @Autowired
    private InmuebleServiceRequest inmuebleServiceRequest;

    @GetMapping
    public ResponseEntity<?> getAllInmueble() {
        return ResponseEntity.ok(inmuebleServiceRequest.getAllInmueble());
    }

    @PostMapping
    public ResponseEntity<?> createInmueble(@RequestBody Object inmueble) {
        return new ResponseEntity<>(
                inmuebleServiceRequest.createInmueble(inmueble),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{inmuebleId}")
    public ResponseEntity<?> deleteInmueble(@PathVariable("inmuebleId") Long inmuebleId) {
        inmuebleServiceRequest.deleteInmueble(inmuebleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
