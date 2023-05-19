package com.company.dtk.microservicesapigateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "inmueble-service",
        path = "/api/inmueble",
        url = "${inmueble.service.url}",
        configuration = FeignConfiguration.class
)
public interface InmuebleServiceRequest {

    @PostMapping
    Object createInmueble(@RequestBody Object requestObject);

    @DeleteMapping
    void deleteInmueble(@PathVariable("inmuebleId") Long inmuebleId);

    @GetMapping()
    List<Object> getAllInmueble();
}
