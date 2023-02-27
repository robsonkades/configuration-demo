package com.example.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenants")
public class TenantController {


    @Autowired
    private ServiceRuntimeConfiguration serviceRuntimeConfiguration;


    @GetMapping(value = "/{id}")
    public ResponseEntity<Void> index(@RequestHeader(value = "x-tenant") String tenant) {


        System.out.println(serviceRuntimeConfiguration.getDbUrl());


        return ResponseEntity.ok().build();
    }
}
