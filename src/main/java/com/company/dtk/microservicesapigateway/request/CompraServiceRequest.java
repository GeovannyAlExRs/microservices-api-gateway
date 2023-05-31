package com.company.dtk.microservicesapigateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        value = "compra-service",
        path = "/api/compra",
        //url = "${compra.service.url}",
        configuration = FeignConfiguration.class
)
public interface CompraServiceRequest {

    @PostMapping
    Object createCompra(@RequestBody Object compra);

    @GetMapping("{userId}")
    List<Object> getAllCompraOfUser(@PathVariable("userId") Long userId);
}
